import { useEffect, useState } from "react";
import api from "../api";

function Rules() {
  const [services, setServices] = useState([]);
  const [serviceId, setServiceId] = useState("");
  const [metricType, setMetricType] = useState("RESPONSE_TIME");
  const [operator, setOperator] = useState(">");
  const [threshold, setThreshold] = useState("");
  const [breachLimit, setBreachLimit] = useState("");

  useEffect(() => {
    api.get("/services").then(res => setServices(res.data));
  }, []);

  const saveRule = async () => {
    if (!serviceId || !threshold || !breachLimit) {
      alert("Please fill all fields");
      return;
    }
    try {
      await api.post(`/rules/${serviceId}`, {
        metricType,
        operator,
        threshold: parseFloat(threshold),
        breachCountLimit: parseInt(breachLimit)
      });
      alert("Rule saved successfully");
      setThreshold("");
      setBreachLimit("");
    } catch (error) {
      alert("Failed to save rule: " + error.message);
    }
  };

  return (
    <div>
      <h2>Configure Monitoring Rules</h2>

      <div className="form-card">
        <div className="form-group">
          <label>Select Service</label>
          <select value={serviceId} onChange={e => setServiceId(e.target.value)}>
            <option value="">Choose a service...</option>
            {services.map(s => (
              <option key={s.id} value={s.id}>
                {s.serviceName}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Metric Type</label>
          <select value={metricType} onChange={e => setMetricType(e.target.value)}>
            <option value="RESPONSE_TIME">Response Time (ms)</option>
            <option value="FAILURE_COUNT">Failure Count</option>
            <option value="HEALTH">Health Score</option>
          </select>
        </div>

        <div className="form-group">
          <label>Operator</label>
          <select value={operator} onChange={e => setOperator(e.target.value)}>
            <option value=">">Greater Than (&gt;)</option>
            <option value="<">Less Than (&lt;)</option>
            <option value=">=">Greater or Equal (&gt;=)</option>
            <option value="<=">Less or Equal (&lt;=)</option>
          </select>
        </div>

        <div className="form-group">
          <label>Threshold Value</label>
          <input
            type="number"
            placeholder="Enter threshold value"
            value={threshold}
            onChange={e => setThreshold(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>Breach Count Limit</label>
          <input
            type="number"
            placeholder="How many violations before alert?"
            value={breachLimit}
            onChange={e => setBreachLimit(e.target.value)}
          />
        </div>

        <button onClick={saveRule}>Save Rule</button>
      </div>
    </div>
  );
}

export default Rules;
