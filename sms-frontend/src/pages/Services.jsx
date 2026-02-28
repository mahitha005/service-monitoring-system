import { useEffect, useState } from "react";
import api from "../api";

function Services() {
  const [services, setServices] = useState([]);
  const [serviceName, setServiceName] = useState("");

  const loadServices = () => {
    api.get("/services").then(res => setServices(res.data));
  };

  useEffect(() => {
    loadServices();
  }, []);

  const addService = async () => {
    if (!serviceName) return;
    await api.post("/services", { serviceName });
    setServiceName("");
    loadServices();
  };

  return (
    <div>
      <h2>Services</h2>

      <div style={{ marginBottom: "20px" }}>
        <input
          placeholder="Service Name"
          value={serviceName}
          onChange={(e) => setServiceName(e.target.value)}
          style={{ marginRight: "10px" }}
        />
        <button onClick={addService}>Add Service</button>
      </div>

      <div className="services">
        {services.map(s => (
          <div key={s.id} className="service-box">
            <h3>{s.serviceName}</h3>
            <p><strong>Service ID:</strong> {s.id}</p>
            <p><strong>API Key:</strong></p>
            <input 
              type="text" 
              value={s.apiKey || "Generating..."} 
              readOnly 
              style={{ fontSize: "12px", width: "100%", marginBottom: "10px" }}
            />
            <p>
              <span className={`badge ${s.status === 'HEALTHY' ? 'green' : 'red'}`}>
                {s.status}
              </span>
            </p>
            <p>Health: {s.healthScore}%</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Services;
