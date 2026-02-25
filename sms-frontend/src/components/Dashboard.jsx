import { useEffect, useState } from "react";
import api from "../api";

function Dashboard() {
  const [stats, setStats] = useState({});

  useEffect(() => {
    api.get("/dashboard").then(res => setStats(res.data));
  }, []);

  return (
    <div>
      <h2>Dashboard</h2>
      <div className="stats">
        <div className="stat-box">
          <h3>Total Services</h3>
          <div className="number">{stats.totalServices || 0}</div>
        </div>
        <div className="stat-box">
          <h3>Healthy</h3>
          <div className="number">{stats.healthyServices || 0}</div>
        </div>
        <div className="stat-box">
          <h3>Unhealthy</h3>
          <div className="number">{stats.unhealthyServices || 0}</div>
        </div>
        <div className="stat-box">
          <h3>Active Alerts</h3>
          <div className="number">{stats.activeAlerts || 0}</div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;