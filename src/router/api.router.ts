import express, { type Request, type Response } from "express";
const apiRouter = express.Router();

import * as apiController from "../controller/api/api.controller.js";
import * as authController from "../controller/api/auth/api.auth.controller.js";
import * as recipeController from "../controller/api/recipe/api.recipe.controller.js";
import { verifyRefreshToken } from "../middleware/verifyUser.js";
import { main } from "../controller/android/api.android.main.controller.js";

apiRouter.get("/test", apiController.test);

apiRouter.post("/auth/login", authController.login);

apiRouter.post("/recipe/add", verifyRefreshToken, recipeController.addRecipe);
apiRouter.get("/recipe/list", verifyRefreshToken, recipeController.listRecipes);

apiRouter.get("/android/main", main);



apiRouter.use(apiController._404);

export default apiRouter;