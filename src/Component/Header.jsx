//import React from 'react';
 import '../Design/Header.css';

const Header = () => {
  return (
    <header className="header">
      <nav>
        <ul className="nav-links">
          <li id="about" className='buttons'>About</li>
          <li id="contact" className='buttons'>Contact</li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
