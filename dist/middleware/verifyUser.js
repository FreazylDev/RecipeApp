import jwt from "jsonwebtoken";
import { User } from "../models/user/User.js";
import { TokenService } from "../services/TokenService.js";
export const verifyRefreshToken = async (req, res, next) => {
    const user = await TokenService.verifyUser(req.headers.authorization);
    if (user === 400) {
        return res.status(400).json({ "auth failed": "Bad request (400)" });
    }
    else if (user === 401) {
        return res.status(401).json({ "auth failed": "Unauthorized (401)" });
    }
    req.userId = user?.id;
    next();
};
//# sourceMappingURL=verifyUser.js.map