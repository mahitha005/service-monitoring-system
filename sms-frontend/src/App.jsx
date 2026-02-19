import { useState } from "react";
import api from "./api";

function App() {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const login = async () => {
    try {
      const response = await api.post("/auth/login", {
        username,
        password
      });

      localStorage.setItem("token", response.data);
      alert("Login successful ✅");
    } catch (error) {
      alert("Login failed ❌");
    }
  };

  const getServices = async () => {
    try {
      const response = await api.get("/services");
      setMessage(JSON.stringify(response.data));
    } catch (error) {
      setMessage("Access denied ❌");
    }
  };

  return (
    <div style={{ padding: "50px" }}>
      <h2>Login</h2>

      <input
        placeholder="Username"
        onChange={(e) => setUsername(e.target.value)}
      />

      <br /><br />

      <input
        type="password"
        placeholder="Password"
        onChange={(e) => setPassword(e.target.value)}
      />

      <br /><br />

      <button onClick={login}>Login</button>

      <br /><br />

      <button onClick={getServices}>Get Services</button>

      <h3>{message}</h3>
    </div>
  );
}

export default App;
