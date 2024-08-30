/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#0866ff',
        text: '#050505',
        grey: '#65676b',
        grey2: '#e4e6eb',
        hover: '#e3e5e8',
      },
      boxShadow: {
        'around': '0 0 5px rgba(0, 0, 0, 0.3)',
      },
    },
  },
  plugins: [],
}

