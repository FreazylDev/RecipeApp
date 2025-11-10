import { type Request, type Response } from "express";
export declare class UserData {
    username: string;
    phoneNumber: string;
    role: string;
    constructor(username: string, phoneNumber: string, role: string);
}
export declare const login: (req: Request, res: Response) => Promise<void>;
//# sourceMappingURL=api.auth.controller.d.ts.map