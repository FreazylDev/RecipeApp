import jwt from "jsonwebtoken";
import { type Response } from "express";
import dotenv from "dotenv";
import { Document, Types } from "mongoose";
import { User, type IUser } from "../models/User.js";

dotenv.config();

const JWT_SECRET = process.env.JWT_KEY as string;
if (!JWT_SECRET) throw new Error("JWT_KEY not set in .env");

export type UserDocument = Document<unknown, {}, IUser> & IUser & { _id: Types.ObjectId };

export class TokenService {
    static setToken(payload: object, expiresIn: number) {
        return jwt.sign(payload, JWT_SECRET, {
            expiresIn 
        })
    }

    static setCookie(res: Response, key: string, payload: object, lengthStr: string) {
        const length = this.calcLength(lengthStr);
        const token = this.setToken(payload, length);
        return res.cookie(key, token, {
            maxAge: length
        })
    }

    private static decodeToken(token: string, checkAdmin = false) {
        try {
            const decoded = jwt.verify(token, JWT_SECRET) as { id: string, role?: string };

            return checkAdmin
                ? { _id: decoded.id, role: decoded.role }
                : { _id: decoded.id };
        } catch (err) {
            return 401;
        }
    }

    static async verifyUser(token: string | undefined, checkAdmin = false): Promise<UserDocument | 401 | 403> {
        if (!token?.startsWith("Bearer ") || !token?.split(" ")[1]) {
            return 401;
        }
        token = token.split(" ")[1] as string;
        const decodedToken = this.decodeToken(token, checkAdmin);

        if (decodedToken === 401) return 401;        
        try {
            const user = await User.findById(decodedToken._id);
            if (!user) return 401;

            if (checkAdmin) {
                if (user.role !== "admin") return 403;
            }
            return user;

        } catch (err) {
            return 401;
        }
    }


    private static calcLength(length: string): number {
        const match = length.match(/^(\d+)([a-zA-Z])$/);
        if (!match) return -1;

        const num = Number(match[1]);
        const unit = match[2]!;

        const multipliers: Record<string, number> = {
            s: 1000,
            m: 1000 * 60,
            h: 1000 * 60 * 60,
            d: 1000 * 60 * 60 * 24
        }

        return multipliers[unit] ? num * multipliers[unit] : -1;
    }
}