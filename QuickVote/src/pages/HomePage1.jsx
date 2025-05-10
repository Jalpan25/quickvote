import React from 'react'
import header from "../Component/Header.jsx"
import HomePage from '../Component/HomePage.jsx'
import Header from '../Component/Header.jsx'
import Features from '../Component/Features.jsx';
import CTASection from '../Component/CTASection.jsx';
import HowItWorks from '../Component/HowItWorks.jsx';
import About from '../Component/About.jsx';
import Footer from '../Component/Footer.jsx';
export default function 
() {
  return (
    <div>
        <Header/>
        <HomePage/>
        <div id="why-choose-us">
  < Features/>
</div>
        <CTASection/>
<div id="how-it-works">
  <HowItWorks />
</div>
<About/>

        <Footer/>
    </div>
  )
}
