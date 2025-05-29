 window.addEventListener('DOMContentLoaded', () => {
   // Funções para gerar dados aleatórios

   const randomFromArray = (arr) => arr[Math.floor(Math.random() * arr.length)];

   const randomName = () => {
     const firstNames = ['Maria', 'Ana', 'Beatriz', 'Clara', 'Juliana', 'Larissa', 'Paula', 'Renata', 'Sofia', 'Tatiana'];
     const lastNames = ['Silva', 'Souza', 'Oliveira', 'Costa', 'Pereira', 'Almeida', 'Lima', 'Gomes', 'Ribeiro', 'Martins'];
     return `${randomFromArray(firstNames)} ${randomFromArray(lastNames)}`;
   };

   const randomAge = () => Math.floor(Math.random() * (50 - 18 + 1)) + 18;

   const randomCity = () => randomFromArray(['São Paulo', 'Rio de Janeiro', 'Belo Horizonte', 'Curitiba', 'Porto Alegre', 'Salvador', 'Brasília', 'Recife']);

   const randomDescription = () => {
     const descriptions = [
       "Sou desenvolvedora apaixonada por tecnologia e inovação, com experiência em front-end e back-end. Gosto de trabalhar em equipes colaborativas, sempre buscando soluções eficientes e criativas para os desafios.",
       "Profissional dedicada com forte background em desenvolvimento de software e interesse em aprendizado contínuo para aprimorar habilidades técnicas e contribuir em projetos desafiadores.",
       "Apaixonada por código limpo e boas práticas, com experiência em várias linguagens e frameworks. Sempre em busca de soluções que entreguem valor ao usuário final.",
       "Desenvolvedora focada em criar interfaces intuitivas e sistemas robustos, valorizando colaboração e melhoria constante.",
       "Entusiasta de tecnologia com experiência em projetos full-stack e desejo de crescer em ambientes dinâmicos e inovadores."
     ];
     return randomFromArray(descriptions);
   };

   const randomLinkedIn = (name) => {
     const formattedName = name.toLowerCase().replace(/\s+/g, '');
     return `https://linkedin.com/in/${formattedName}`;
   };

   const randomGitHub = (name) => {
     const formattedName = name.toLowerCase().replace(/\s+/g, '');
     return `https://github.com/${formattedName}`;
   };

   const randomPosition = () => randomFromArray([
     "Backend Developer",
     "Frontend Developer",
     "Full Stack Developer",
     "Data Scientist",
     "DevOps Engineer",
     "Mobile Developer"
   ]);

   const randomLevel = () => randomFromArray([
     "internship",
     "trainee",
     "junior",
     "pleno",
     "senior",
     "specialist"
   ]);

   const randomPreference = () => randomFromArray([
     "company",
     "online",
     "hybrid"
   ]);

   const randomObjective = () => randomFromArray([
     "first_oportunity",
     "looking_oportunity",
     "career_transition",
     "return_market"
   ]);

   const randomExpectation = () => (Math.floor(Math.random() * (15000 - 2000 + 1)) + 2000).toFixed(2);

   const randomTechnology = () => randomFromArray([
     "Java, Spring Boot, Thymeleaf",
     "React, Node.js, MongoDB",
     "Python, Django, PostgreSQL",
     "Angular, .NET Core, Azure",
     "Flutter, Dart, Firebase"
   ]);

   // Preencher campos aleatórios

   const name = randomName();

   document.querySelector('#email').value = `${name.toLowerCase().replace(/\s+/g, '.')}@exemplo.com`;
   document.querySelector('#password').value = 'senhaSegura123'; // Pode ser fixo
   document.querySelector('#aboutName').value = name;
   document.querySelector('#age').value = randomAge();
   document.querySelector('#city').value = randomCity();
   document.querySelector('#description').value = randomDescription();
   document.querySelector('#linkedin').value = randomLinkedIn(name);
   document.querySelector('#github').value = randomGitHub(name);
   document.querySelector('#position').value = randomPosition();
   document.querySelector('#level').value = randomLevel();
   document.querySelector('#preference').value = randomPreference();
   document.querySelector('#objective').value = randomObjective();
   document.querySelector('#expectation').value = randomExpectation();
   document.querySelector('#technology').value = randomTechnology();

   // Força update dos selects (alguns navegadores podem precisar)
   document.querySelector('#level').dispatchEvent(new Event('change'));
   document.querySelector('#preference').dispatchEvent(new Event('change'));
   document.querySelector('#objective').dispatchEvent(new Event('change'));

   // Preview da imagem selecionada (mantive seu código)
   const photoInput = document.getElementById('photo');
   const preview = document.getElementById('preview');

   photoInput.addEventListener('change', function(event) {
     const [file] = event.target.files;
     if (file) {
       preview.src = URL.createObjectURL(file);
       preview.style.display = 'block';
     } else {
       preview.style.display = 'none';
     }
   });

 });