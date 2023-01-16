import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Register() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();

        const response = await fetch("http://localhost:8080/create_account", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: email,
                password
            })
        });

        if (response.status === 201) {
            navigate("/login");
        } else if (response.status === 400) {
            console.log("Error sending...");
        } else {
            console.log("Weird error!");
        };
    }

    return (
        <>
            <h2>Register</h2>

            <form onSubmit={handleSubmit}>
                <label htmlFor="user-email">Email:</label>
                <input type="email" id="user-email" onChange={(event) => setEmail(event.target.value)} />
                <label htmlFor="user-password">Password</label>
                <input type="password" id="user-password" onChange={(event) => setPassword(event.target.value)} />
                <br />
                <button type="submit">Register</button>
            </form>
        </>
    );
}

export default Register;