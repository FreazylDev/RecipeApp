import express, {} from "express";
const devRouter = express.Router();
import * as devController from "../controller/dev.controller.js";
import { verifyAdmin } from "../middleware/verifyUser.js";
devRouter.use(verifyAdmin);
devRouter.get("/test", devController.test);
devRouter.get("/add-user", devController.addUser);
devRouter.use(devController._404);
export default devRouter;
//# sourceMappingURL=dev.router.js.map