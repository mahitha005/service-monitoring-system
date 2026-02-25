import { useEffect, useState } from "react";
import api from "../api";

function Alerts() {
  const [alerts, setAlerts] = useState([]);

  const loadAlerts = () => {
    api.get("/alerts").then(res => setAlerts(res.data));
  };

  useEffect(() => {
    loadAlerts();
  }, []);

  const acknowledge = async (id) => {
    await api.put(`/alerts/${id}/ack`);
    loadAlerts();
  };

  const resolve = async (id) => {
    await api.put(`/alerts/${id}/resolve`);
    loadAlerts();
  };

  return (
    <div>
      <h2>Alerts</h2>
      <div className="alerts">
        {alerts.map(a => (
          <div key={a.id} className="alert-box">
            <div>
              <h3>{a.type}</h3>
              <p>{a.message}</p>
              <p>Status: {a.status} | Count: {a.count}</p>
            </div>
            <div>
              {a.status === "OPEN" && (
                <>
                  <button onClick={() => acknowledge(a.id)}>Ack</button>
                  <button onClick={() => resolve(a.id)}>Resolve</button>
                </>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Alerts;