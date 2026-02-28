import { useNavigate, useLocation } from "react-router-dom";

function Sidebar() {
  const navigate = useNavigate();
  const location = useLocation();
  const currentPath = location.pathname;

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div className="sidebar">
      <h1>📊 Monitor</h1>

      <button className={currentPath === "/dashboard" ? "active" : ""} onClick={() => navigate("/dashboard")}>
        Dashboard
      </button>

      <button className={currentPath === "/services" ? "active" : ""} onClick={() => navigate("/services")}>
        Services
      </button>

      <button className={currentPath === "/rules" ? "active" : ""} onClick={() => navigate("/rules")}>
        Rules
      </button>

      <button className={currentPath === "/alerts" ? "active" : ""} onClick={() => navigate("/alerts")}>
        Alerts
      </button>

      <button className="logout" onClick={logout}>
        Logout
      </button>
    </div>
  );
}

export default Sidebar;