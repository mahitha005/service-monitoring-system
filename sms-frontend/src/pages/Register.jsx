import { useState } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";

function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const navigate = useNavigate();

  const register = async () => {
    try {
      await api.post("/auth/register", {
        username,
        email,
        password
      });

      alert("OTP sent to your email!");

      localStorage.setItem("verifyUser", username);
      navigate("/verify");

    } catch (err) {
        alert(
          err.response?.data?.message ||
          err.response?.data?.error ||
          "Registration failed"
        );
    }
  };

  return (
    <div className="login-page">
      <div className="login-box">
        <h2>Create Account</h2>
        <input
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button onClick={register}>Create Account</button>
        <p>
          Already have an account?
          <span onClick={() => navigate("/")}> Login</span>
        </p>
      </div>
    </div>
  );
}

export default Register;