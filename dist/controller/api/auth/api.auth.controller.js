import {} from "express";
import { User } from "../../../models/user/User.js";
import { handleAuthErrors } from "./api.auth.errors.js";
import { TokenService } from "../../../services/TokenService.js";
const ACCESS_TOKEN_EXP = 10 * 60 * 1000; // 10 minutes in ms
const REFRESH_TOKEN_EXP = 30 * 24 * 60 * 60 * 1000; // 30 days in ms
const JWT_SECRET = "123";
export class UserData {
    username;
    phoneNumber;
    role;
    constructor(username, phoneNumber, role) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.role = role || "user";
    }
}
export const login = async (req, res) => {
    const { username, phoneNumber } = req.body;
    const userData = new UserData(username, phoneNumber, "user");
    try {
        const user = await User.login(userData);
        TokenService.setCookie(res, "refresh_token", {
            id: user._id
        }, "30d");
        if (!user.activated) {
            await User.findOneAndUpdate({ _id: user._id }, { activated: true }, { new: true });
        }
        res.status(201).json(user);
    }
    catch (err) {
        err = handleAuthErrors(err.message);
        res.json(err);
    }
};
//# sourceMappingURL=api.auth.controller.js.map