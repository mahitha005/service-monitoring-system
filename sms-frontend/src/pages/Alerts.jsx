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
      <h2>Active Alerts</h2>
      {alerts.length === 0 ? (
        <p style={{ textAlign: "center", color: "#65676b", marginTop: "40px" }}>
          No alerts found
        </p>
      ) : (
        <div className="alerts">
          {alerts.map(a => (
            <div key={a.id} className={`alert-box ${a.type === 'CRITICAL' ? 'critical' : ''}`}>
              <div>
                <h3>{a.type.replace(/_/g, ' ')}</h3>
                <p>{a.message}</p>
                <p style={{ fontSize: "13px", marginTop: "8px" }}>
                  Status: <strong>{a.status}</strong> | Count: <strong>{a.count}</strong>
                </p>
              </div>
              <div>
                {a.status === "OPEN" && (
                  <>
                    <button className="btn-secondary" onClick={() => acknowledge(a.id)}>
                      Acknowledge
                    </button>
                    <button className="btn-success" onClick={() => resolve(a.id)}>
                      Resolve
                    </button>
                  </>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Alerts;
