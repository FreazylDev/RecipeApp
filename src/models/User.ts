import mongoose, { Model, Types, Document } from "mongoose";
import bcrypt from "bcryptjs";
import validator from "validator";
import type { UserData } from "../controller/api/auth/api.auth.controller.js";

const { isEmail } = validator;

export interface IUser extends Document {
    _id: Types.ObjectId;
    username: string;
    email: string;
    role: string;
    createdAt: Date;
    updatedAt: Date;
    activated: boolean;
}

interface IUserModel extends Model<IUser> {
    login(userData: UserData): Promise<IUser>;
}

const userSchema = new mongoose.Schema<IUser>({
    username: {
        type: String,
        required: [true, "username not set"],
        unique: true,
    },
    email: {
        type: String,
        required: [true, "email not set"],
        unique: true,
        validate: [isEmail, "not an email"]
    },
    role: {
        type: String,
        enum: ["user", "admin"],
        default: "user"
    },
    activated: {
        type: Boolean,
        default: false
    }
}, { timestamps: true }
)

userSchema.statics.login = async function(userData: UserData) {
    const user = await this.findOne({ username: userData.username });
    if (!user) throw Error("user not found");

    const auth = await bcrypt.compare(userData.email, user.email);
    if (!auth) throw Error("incorrect email");
    return user;
}

userSchema.pre("save", async function(this) {
    const salt = await bcrypt.genSalt();
    this.email = await bcrypt.hash(this.email, salt);
});


export const User: IUserModel = mongoose.model<IUser, IUserModel>("user", userSchema);