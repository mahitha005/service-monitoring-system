import { useState } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const login = async () => {
    try {
      const response = await api.post("/auth/login", { username, password });
      localStorage.setItem("token", response.data);
      navigate("/dashboard");
    } catch (error) {
      alert("Login failed");
    }
  };

  return (
    <div className="login-page">
      <div className="login-box">
        <h2>Service Monitor</h2>
        <input
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          onKeyPress={(e) => e.key === 'Enter' && login()}
        />
        <button onClick={login}>Login</button>
      </div>
    </div>
  );
}

export default Login;