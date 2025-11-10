import jwt from "jsonwebtoken";
import {} from "express";
import dotenv from "dotenv";
import { Document, Types } from "mongoose";
import { User } from "../models/user/User.js";
dotenv.config();
const JWT_SECRET = process.env.JWT_KEY;
if (!JWT_SECRET)
    throw new Error("JWT_KEY not set in .env");
export class TokenService {
    static setToken(payload, expiresIn) {
        return jwt.sign(payload, JWT_SECRET, {
            expiresIn
        });
    }
    static setCookie(res, key, payload, lengthStr) {
        const length = this.calcLength(lengthStr);
        const token = this.setToken(payload, length);
        return res.cookie(key, token, {
            maxAge: length
        });
    }
    static decodeToken(token, checkAdmin = false) {
        try {
            const decoded = jwt.verify(token, JWT_SECRET);
            return checkAdmin
                ? { _id: decoded.id, role: decoded.role }
                : { _id: decoded.id };
        }
        catch (err) {
            return 401;
        }
    }
    static sendStatusCode(code) {
        return { err: "Error", statusCode: code };
    }
    static async verifyUser(token, checkAdmin = false) {
        if (!token?.startsWith("Bearer ") || !token?.split(" ")[1]) {
            return 401;
        }
        token = token.split(" ")[1];
        const decodedToken = this.decodeToken(token, checkAdmin);
        if (decodedToken === 401)
            return 401;
        try {
            const user = await User.findById(decodedToken._id);
            if (!user)
                return 401;
            if (checkAdmin) {
                if (user.role !== "admin")
                    return 403;
            }
            return user;
        }
        catch (err) {
            return 401;
        }
    }
    static calcLength(length) {
        const match = length.match(/^(\d+)([a-zA-Z])$/);
        if (!match)
            return -1;
        const num = Number(match[1]);
        const unit = match[2];
        const multipliers = {
            s: 1000,
            m: 1000 * 60,
            h: 1000 * 60 * 60,
            d: 1000 * 60 * 60 * 24
        };
        return multipliers[unit] ? num * multipliers[unit] : -1;
    }
}
//# sourceMappingURL=TokenService.js.map