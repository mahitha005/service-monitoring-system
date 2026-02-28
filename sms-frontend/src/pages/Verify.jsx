import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import api from "../api";

function Verify() {
  const [otp, setOtp] = useState("");
  const location = useLocation();
  const navigate = useNavigate();

  const username = localStorage.getItem("verifyUser");
  console.log("Username:", username);

  const verifyOtp = async () => {
    if (!otp) {
      alert("Please enter OTP");
      return;
    }

    try {
      const response = await api.post("/auth/verify", {
        username,
        otp
      });

      alert(response.data?.message || "Verified successfully!");
      navigate("/");

    } catch (err) {
      console.log(err.response); // 🔍 for debugging

      alert(
        err.response?.data?.message ||
        err.response?.data?.error ||
        JSON.stringify(err.response?.data) ||
        "OTP verification failed"
      );
    }
  };

  return (
    <div className="login-page">
      <div className="login-box">
        <h2>Verify OTP</h2>

        <input
          placeholder="Enter OTP"
          value={otp}
          onChange={(e) => setOtp(e.target.value)}
        />

        <button onClick={verifyOtp}>Verify</button>

        <p>
          Back to <span onClick={() => navigate("/")}>Login</span>
        </p>
      </div>
    </div>
  );
}

export default Verify;