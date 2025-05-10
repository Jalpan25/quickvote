import React from 'react';

const steps = [
  { number: "01", title: "Register", description: "Create your account or log in to your existing one" },
  { number: "02", title: "Create Election", description: "Set up your election with candidates and voting rules" },
  { number: "03", title: "Invite Voters", description: "Send secure invitations to your voting population" },
  { number: "04", title: "Vote", description: "Participants cast their votes securely from any device" },
  { number: "05", title: "Results", description: "View accurate results in real-time as votes are tallied" }
];

const HowItWorks = () => {
  return (
    <section className="relative w-full py-20 bg-gradient-to-r from-blue-50 to-purple-50 overflow-hidden">
      {/* Background Animation */}
      <div className="absolute inset-0 overflow-hidden pointer-events-none">
        <div className="absolute w-[400px] h-[400px] bg-blue-400 opacity-20 blur-[150px] animate-pulse rounded-full top-[-100px] left-[-100px]"></div>
        <div className="absolute w-[500px] h-[500px] bg-purple-400 opacity-30 blur-[150px] animate-pulse rounded-full bottom-[-150px] right-[-100px]"></div>
      </div>

      <div className="container mx-auto px-6 text-center relative z-10">
        {/* Section Heading */}
        <h2 className="text-4xl md:text-5xl font-extrabold text-blue-700 mb-12 tracking-wide">
          How It Works
        </h2>

        {/* Step Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-10">
          {steps.map((step, index) => (
            <div
              key={index}
              className="group bg-gradient-to-br from-blue-100 to-purple-100 p-8 rounded-2xl shadow-lg transform transition-transform duration-300 hover:scale-105 hover:shadow-2xl relative"
            >
              {/* Step Number with Animation */}
              <div className="absolute top-[-20px] left-[-20px] bg-gradient-to-br from-blue-500 to-purple-500 text-white w-16 h-16 flex items-center justify-center rounded-full text-xl font-bold shadow-md animate-bounce">
                {step.number}
              </div>

              {/* Step Title */}
              <h3 className="text-2xl font-semibold mb-4 text-gray-800 tracking-wide">
                {step.title}
              </h3>

              {/* Step Description */}
              <p className="text-lg text-gray-600 leading-relaxed">
                {step.description}
              </p>

              {/* Hover Effect */}
              <div className="absolute inset-0 bg-blue-400 opacity-0 group-hover:opacity-10 transition duration-300 rounded-2xl"></div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default HowItWorks;
