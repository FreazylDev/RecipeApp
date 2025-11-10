import { type Request, type Response } from "express";
import { Recipe } from "../../../models/recipe/Recipe.js";
import { handleRecipeErrors } from "./api.recipe.errors.js";
import { error } from "console";


export const addRecipe = async (req: Request, res: Response) =>  {
    const { title, frontImage, ingredients, contents, tags } = req.body;
    const { userId } = req;
    console.log("Hello")
    try {
        const recipe = await Recipe.create({
            author: userId,
            body: {
                title, frontImage: frontImage || null, ingredients, contents,  tags
            }
        });
        console.log(recipe);
        res.status(200).json(recipe);
    } catch (err) {
        err = handleRecipeErrors(err);
        res.status(501).json(err);
    }
}

export const listRecipes = async (req: Request, res: Response) => {
    const limit = Number(req.query.index) || 25;
    const q = typeof req.query.q === "string" ? req.query.q.trim() : "";

    let recipes;
    if (q) {
        recipes = await Recipe.find({
            $or: [
                { "body.title": { $regex: q, $options: "i" } },
                { "body.tags": { $regex: q, $options: "i" } },
                { "body.ingredients": { $regex: q, $options: "i" } },
            ]
        }).limit(limit);
    } else {
        recipes = await Recipe.find({}).limit(limit);
    }
    if (!recipes || recipes.length === 0) return res.status(404).json({ err: "Nothing found" });
    res.status(200).json(recipes);
}