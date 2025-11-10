import { type Request, type Response } from "express";
import dotenv from "dotenv";
import { User } from "../../models/User.js";
import { TokenService } from "../../services/TokenService.js";

dotenv.config();

const BASE_URL = process.env.BASE_URL as string;
if (!BASE_URL) throw new Error("BASE_URL not set in .env");

const _fetch = async (path: string, token?: string) => {
    const url = `${BASE_URL}${path}`;
    const response = await fetch(url, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    });
    
    if (!response.ok) throw new Error(`Fetch failed: ${response.status}`);
    return await response.json();
}

export const main = async (req: Request, res: Response) => {
    const token = req.headers.authorization?.split(" ")[1];
    if (!token || typeof TokenService.verifyUser(token) !== "number") {
        const userOptions = await User.find({ activated: false });
        const users = userOptions.map(user => user.username)
        return res.status(401).json(users);
    }
    try {
        const recipes = await _fetch("/api/recipe/list", token);
        res.status(201).json( { recipes, appVersion: "1.0.0" });
    } catch (err) {
        res.status(401).json({ errorMessage: (err as Error).message, errorCause: (err as Error).cause || "Idk" });
    }
}