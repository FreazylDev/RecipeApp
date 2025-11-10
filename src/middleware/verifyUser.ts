import type { Request, Response, NextFunction } from "express";
import jwt from "jsonwebtoken";

import { User } from "../models/User.js";
import { TokenService } from "../services/TokenService.js";

declare global {
    namespace Express {
        interface Request {
            userId?: string;
        }
    }
}

const handleErrors = (res: Response, code: number) => {
    if (code === 400) {
        return res.status(400).json({ "auth failed": "Bad request", "statusCode": 400 });
    } else if (code === 401) {
        return res.status(401).json({ "auth failed": "Unauthorized", "statusCode": 401 });
    } else if (code === 403) {
        return res.status(403).json({ "auth failed": "Forbidden", "statusCode": 403 })
    }
}


export const verifyRefreshToken = async (req: Request, res: Response, next: NextFunction) => {
    const user = await TokenService.verifyUser(req.headers.authorization);
    if (typeof user === "number") {
        return handleErrors(res, user);
    };
    
    req.userId = user.id;
    next();
}

export const verifyAdmin = async (req: Request, res: Response, next: NextFunction) => {

    const user = await TokenService.verifyUser(req.headers.authorization, true);

    if (typeof user === "number") return handleErrors(res, user);
    
    req.userId = user.id;
    next();
}