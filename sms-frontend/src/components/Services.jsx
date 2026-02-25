import { useEffect, useState } from "react";
import api from "../api";

function Services() {
  const [services, setServices] = useState([]);

  useEffect(() => {
    api.get("/services").then(res => setServices(res.data));
  }, []);

  return (
    <div>
      <h2>Services</h2>
      <div className="services">
        {services.map(s => (
          <div key={s.id} className="service-box">
            <h3>{s.serviceName}</h3>
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