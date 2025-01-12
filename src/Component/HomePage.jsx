import '../Design/HomePage.css';
import voteImage from '../assets/ps.png';
import { useEffect } from 'react';

const HomePage = () => {
    useEffect(() => {
        document.body.style.overflow = 'hidden'; 
        return () => {
          document.body.style.overflow = 'auto';
        };
      }, []);

  return (
    <div className="homepage" style={{ backgroundColor: '#F5EFE7' }}>
      <div className="left-section">
      <img
        src={voteImage}
        alt="Vote Icon"
        className="icon"
      />
      </div>
      <div className="right-section">
        <h3>Be a part of decision</h3>
        <h1>Vote Today</h1>
        <div className="buttons">
          <button className="admin-btn">Admin</button>
          <button className="participant-btn">Participant</button>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
