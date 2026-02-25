function Sidebar({ setPage }) {
  return (
    <div className="sidebar">
      <h1>Service Monitor</h1>
      <button onClick={() => setPage("dashboard")}>Dashboard</button>
      <button onClick={() => setPage("services")}>Services</button>
      <button onClick={() => setPage("alerts")}>Alerts</button>
    </div>
  );
}

export default Sidebar;