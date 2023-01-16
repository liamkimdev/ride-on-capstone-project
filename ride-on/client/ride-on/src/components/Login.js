import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();
    const auth = useContext(AuthContext);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const response = await fetch("http://localhost:8080/authenticate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: email,
                password
            })
        });

        if (response.status === 200) {
            const { jwt_token } = await response.json();
            auth.login(jwt_token);
            navigate("/");
        } else if (response.status === 403) {
            console.log("Error sending...");
        } else {
            console.log("Weird error!");
        };
    }

    return (
        <>
            <h2>Login</h2>

            <form onSubmit={handleSubmit}>
                <label htmlFor="user-email">Email:</label>
                <input type="email" id="user-email" onChange={(event) => setEmail(event.target.value)} />
                <label htmlFor="user-password">Password</label>
                <input type="password" id="user-password" onChange={(event) => setPassword(event.target.value)} />
                <br />
                <button type="submit">Login</button>
            </form>
        </>
    );
}

export default Login;