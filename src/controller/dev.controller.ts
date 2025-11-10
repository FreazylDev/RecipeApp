import { type Request, type Response } from "express";
import dotenv from "dotenv";
import { UserData } from "./api/auth/api.auth.controller.js";
import { User } from "../models/User.js";
import { handleAuthErrors } from "./api/auth/api.auth.errors.js";

dotenv.config();

const DEV_KEY = process.env.DEV_KEY;
if (!DEV_KEY) throw Error("DEV_KEY not set in .env");



export const test = (req: Request, res: Response) => {
    res.status(200).json({ msg: "Hello Dev!" });
}

export const addUser = async (req: Request, res: Response) => {
    const { username, email, role } = req.body;
    const userData = new UserData(username, email, role);
    try {
        const user = await User.create(userData);
        res.status(200).json({ "user created": user });
    } catch (err) {
        err = handleAuthErrors(err);
        res.status(500).json(err);
    }
    
}

export const _404 = (req: Request, res: Response) => {
    res.status(501).json({ msg: "Not a valid API endpoint" });
}