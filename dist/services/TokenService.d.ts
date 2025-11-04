import { type Response } from "express";
import { Document, Types } from "mongoose";
import { type IUser } from "../models/user/User.js";
type UserDocument = Document<unknown, {}, IUser> & IUser & {
    _id: Types.ObjectId;
};
export declare class TokenService {
    static setToken(payload: object, expiresIn: number): string;
    static setCookie(res: Response, key: string, payload: object, lengthStr: string): Response<any, Record<string, any>>;
    private static decodeToken;
    static verifyUser(token: string | undefined, checkAdmin?: boolean): Promise<UserDocument | 401 | 400 | null>;
    private static calcLength;
}
export {};
//# sourceMappingURL=TokenService.d.ts.map