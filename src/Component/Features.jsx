import "../Design/Features.css"; 

const Features = () => {
  const features = [
    { icon: "fa-lock", text: "Secured Voting" },
    { icon: "fa-check-square", text: "Verifiable Voting" },
    { icon: "fa-thumbs-up", text: "Easy to use" },
    { icon: "fa-dollar-sign", text: "Cheaper than ballot voting system" },
    { icon: "fa-clock", text: "Faster voting process" },
  ];

  return (
    <div className="features-container">
      <h1 className="features-heading">Features</h1>
    
      <ul className="features-list">
        {features.map((feature, index) => (
          <li key={index} className="feature-item">
            <i className={`fa ${feature.icon} feature-icon`} />
            <span className="feature-text">{feature.text}</span>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Features;
