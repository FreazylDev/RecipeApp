const jsonErr = (msg: String): { err: String } => {
    return {
        err: msg
    }
}

export const handleAuthErrors = (err: any) => {
    
    if (err.code === 11000) {
        const { keyPattern } = err;
        const key = Object.keys(keyPattern).toString();
        if (key === "username") return jsonErr("Je hebt al een account aangemaakt");
        if (key === "email") return "Incorrecte email"
    }

    if (err._message === "user validation failed") {
        const msg = err.message || "";
        if (msg.includes("not an email")) {
            return "Geen geldig email"
        } else if (msg.includes("username not set")) {
            return "Voeg een naam toe"
        } else if (msg.includes("email not set")) {
            return "Voeg een email toe"
        }
        return "Validatie fout: " + err
    }

    if (err === "user not found") {
        return "Deze naam bestaat niet";
    }
    if (err === "incorrect email") {
        return "Incorrecte email"
    }

    return err;
}
