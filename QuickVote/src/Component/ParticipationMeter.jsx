import React from "react";

const ParticipationMeter = ({ participationRate }) => (
  <div className="participation-meter">
    <label>Participation Rate</label>
    <div className="meter">
      <div className="meter-fill" style={{ width: `${participationRate}%` }} />
    </div>
    <span>{participationRate.toFixed(1)}%</span>
  </div>
);

export default ParticipationMeter;
