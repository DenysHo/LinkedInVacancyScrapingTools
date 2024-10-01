package com.go.denys.selenium.automatization;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.excel.ExcelWriter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.excel.JobAdToExcelWritingTransformer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcelWriterTest {

    @Test
    public void transformAndWriteTest() {
        List<JobAd> jobAdList = new ArrayList<>();

        jobAdList.add(new JobAd("Java Developer - Intern (Remote Internship - IT)", "Embark on an exciting journey into the world of Java development with an exclusive internship opportunity offered by Kreativstorm, a dynamic and innovative management-consulting firm based in Berlin, Germany. Work remotely with our multinational team and advance your career in Java development!If you're passionate about software development and have strong problem-solving skills, this internship is perfect for you. Under the mentorship of industry experts, you'll tackle diverse Java development projects, exploring concepts like object-oriented programming, web applications, APIs, and software design patterns. Gain hands-on experience in building robust and scalable Java applications, all from the comfort of your home.Internship Overview and Details:Location: Fully RemoteSchedule: Part-Time InternshipDuration: 3 months with the possibility of permanent employment after the internship periodRequirementsIdeal Candidates:Students pursuing a degree or recent graduates in Computer Science, Information Technology, or a related field.Aspiring Java developers with a strong interest in software development.Analytical thinkers adept at solving complex programming challenges.Effective communicators who thrive in collaborative team environments.Detail-oriented individuals excelling in fast-paced, technical settings.Enthusiastic learners committed to refining their Java development skills.Proficiency in Java programming language and related technologies (e.g., Spring, Hibernate) is highly valued.Familiarity with software development lifecycle and best practices advantageous.Some initial industry experience is a big plus.Your Role:Contribute to Java development projects, writing clean and efficient code.Collaborate with the development team to design and implement software solutions.Assist in debugging, testing, and optimizing Java applications.Bring innovative solutions to software development challenges.Foster a collaborative team environment focused on excellence in Java developmentSupport in documenting and presenting software design and solutions.BenefitsWhy Join Us? Kreativstorm is a dynamic and innovative management-consulting firm headquartered in Berlin, Germany. Our team of experienced consultants brings a diverse range of talents and backgrounds, allowing us to provide tailored solutions to businesses of all sizes and industries. As part of the Yes Network Group, LLC, a reputable US holding company, we have access to extensive resources and expertise. Join us and start your journey in Java development today!Apply now and pave the way for your future in Java development!", "Kreativstorm", "https://de.linkedin.com/jobs/view/java-developer-intern-remote-internship-it-at-kreativstorm-4027585893?trk=public_jobs_topcard-title", "Bonn, North Rhine-Westphalia, Germany"));
        jobAdList.add(new JobAd("Java Software Engineer", "Java Software EngineerEuropean Tech Recruit are working closely with an exciting tech company, based in Hamburg, who are looking for a Java Software Engineer to join their team.Responsibilities as Java Software Engineer:Collaborating with cross-functional teams to understand business requirements and technical specifications.Working with legacy codebases, identifying areas for improvement, and implementing enhancements or replacements as needed.Developing and deploying microservices using Java 17 and Spring Boot, adhering to best practices and design principles.Leveraging your expertise in Docker, REST, Git, and Maven/Gradle to create robust and scalable applications.Contributing to the improvement of software development processes and best practices.Applying analytical skills to troubleshoot issues, optimize performance, and ensure the reliability of services.Ensuring basic web service security measures are implemented, protecting applications and users.Collaborating with team members and sharing knowledge to foster a culture of continuous learning.Requirements:You are fluent in English and German.Bachelor's degree in Computer Science or related field (or equivalent work experience).Proficiency in Java programming, including experience with Advanced Java concepts.Strong familiarity with Spring Boot and microservices architecture.Expertise in Docker, REST, Git, and build tools like Maven or Gradle.Ability to understand and work with legacy code, making improvements and replacements as needed.Strong analytical and problem-solving skills.Basic understanding of web service security principles.Desirable experience:Experience with reactive programming and event-driven design.Familiarity with message queues and asynchronous communication patterns.Proficiency with tools such as IntelliJ IDEA.Knowledge of continuous integration and continuous deployment (CI/CD) pipelines.Basic experiences with OSGIExperience in the field of VDV KA standard.If this role is of any interest please apply directly on LinkedIn or send a copy of your CV to nh@eu-recruit.com.By applying to this role you understand that we may collect your personal data and store and process it on our systems. For more information please see our Privacy Notice (https://eu-recruit.com/about-us/privacy-notice/)", "European Tech Recruit", "https://de.linkedin.com/jobs/view/java-software-engineer-at-european-tech-recruit-4028337548?trk=public_jobs_topcard-title", "Hamburg, Germany"));
        jobAdList.add(new JobAd("Software Engineer - €85,000 - Java, SQL, AWS, Kafka, CI/CD – International environment", "Software Engineer - €85,000 - Java, SQL, AWS, Kafka, CI/CD – International environmentMy client is growing their Automatic Content Recognition team and seek a talented Java Developer to join their ranks in Frankfurt.They sit within the AdTech domain and use their niche expertise to enable clients to better reach and understand audiences across screens.The successful candidate will be encouraged to take a proactive and autonomous approach to R&D topics whilst solving challenging problems and having fun, of course!What else will I be up to?Write clean, efficient, and maintainable code in Java and SQL.Develop and maintain software components using Apache Camel and Spring Batch.Implement and integrate messaging systems such as Kafka and RabbitMQ.Develop and maintain CI/CD pipelines for automated builds, tests, and deployments.Ensure code quality and reliability through continuous testing and code reviews.It’s a strong, open-minded, technology-driven team and you’ll have the chance to work with seriously talented people.The training is second-to-none – the managers know what developers need because they code themselves. This is a real technology company where career progression is encouraged, whether that involves people management or architecture.Interested? There are 2 interview spots available so don’t miss out! Apply now!Keywords: Java, SQL, REST API’s, Apache Camel, Spring Batch, CI/CD, containerisation, Kafka, RabbitMQ, JUnit, monitoring, Vertica, MySQL, Docker, Kubernetes, Linux, scalable, reliable, AWS, cloud, Microservices, SonarQube", "Findr", "https://de.linkedin.com/jobs/view/software-engineer-%E2%82%AC85-000-java-sql-aws-kafka-ci-cd-%E2%80%93-international-environment-at-findr-4023063513?trk=public_jobs_topcard-title", "Frankfurt am Main, Hesse, Germany"));
        jobAdList.add(new JobAd("Java / C Programmer (Java Full Stack Engineer)", "Job Title: Java / C Programmer (Java Full Stack Engineer)Job Location: Leipzig, Germany/ HybridJob Type : ContractLanguage: German is mustExperience: 7-10 years.Job Description:Java knowledgeEspecially unit tests, integration tests (ideally with DBunit, Mockito)Java 17HibernateExperience with C-ProgrammingUse of Sonar (optional)Knowledge of Ranorex (optional, could be used to expand test automation of Kompass302Erf)German-speakingRegards,Rachanarachana@falconsmartit.com", "Falcon Smart IT (FalconSmartIT)", "https://de.linkedin.com/jobs/view/java-c-programmer-java-full-stack-engineer-at-falcon-smart-it-falconsmartit-4025870351?trk=public_jobs_topcard-title", "Leipzig, Saxony, Germany"));
        jobAdList.add(new JobAd("Software Engineer", "Description dvhaus is searching for a Java Expert/ Developer.Must have 3+ yrs experience with Java in the FrontendMust have experience with legacy systems and code (check tech requirements below!)Must be able to be on-site in Oberhaching near Munich during the induction periodMust speak German on at least C1 level and be located in GermanyTechnologiesJavaSeleniumRequirementsClient application (front end)Code guidelinesException handlingJSP basic structure regarding accessibilityResponsive behaviorServletscontainersJAX-WSSelenium (test automation)jQueryServer (Back End)LinuxSQLJavaApache Log4jWorking environmentJBOSS EAP 7.4Create JUnit testsRemote debuggingSSO (Single Sign On)Nexus (Maven Repository)EclipseeFiles system (eIp, e2a, VIS)Database administrationResponsibilitiesFrontend development in Java.Implementation of refactoring measures.Bug fixes.Customer contact to clarify technical and functional issues.Modernization, maintenance and further development of complex processes using the latest technologies.Requirements analysis in cooperation with customers, creation of concepts.Recruitment ProcessCultural fit call with Managing Director (≈ 30 minutes)Tech assessment call with Teamlead (≈60 minutes)BenefitsHome Office/ fully remote within Germany after induction periodWork-Life-Balance (no overtime, weekend work, public holiday work)", "ju ucy", "https://de.linkedin.com/jobs/view/software-engineer-4026507219?trk=public_jobs_topcard-title", "Germany"));

        JobAdToExcelWritingTransformer transformer = new JobAdToExcelWritingTransformer();
        List<List<String>> adsToWrite = transformer.transform(jobAdList);

        List<String> headers = new ArrayList<>();
        headers.add("Company Name");
        headers.add("Position");
        headers.add("Location");
        headers.add("Date");
        headers.add("Status");
        headers.add("URL");

        LocalDateTime now = LocalDateTime.now();

        // Определяем форматтер с нужным форматом
        DateTimeFormatter formatterSheet = DateTimeFormatter.ofPattern("dd.MM.yyyy HH-mm");
        String formattedDateTime = now.format(formatterSheet);

        ExcelWriter writer = new ExcelWriter(adsToWrite, headers, "C:\\Users\\gluza\\OneDrive\\Рабочий стол\\jobs.xlsx", formattedDateTime);
        writer.write();
    }


    @Test
    public void writeTest() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = currentDate.format(formatter);


        List<String> headers = new ArrayList<>();
        headers.add("Company Name");
        headers.add("Position");
        headers.add("Location");
        headers.add("Date");
        headers.add("Status");
        headers.add("URL");

        List<String> row1 = new ArrayList<>();
        row1.add("Genedata");
        row1.add("Java Software Engineer");
        row1.add("Munich, Bavaria, Germany");
        row1.add(formattedDate);
        row1.add("geschickt");
        row1.add("https://de.linkedin.com/jobs/view/java-software-engineer-at-genedata-4025233561?trk=public_jobs_topcard-title");

        List<String> row2 = new ArrayList<>();
        row2.add("Falcon Smart IT (FalconSmartIT)");
        row2.add("Java / C Programmer (Java Full Stack Engineer)");
        row2.add("Leipzig, Saxony, Germany");
        row2.add(formattedDate);
        row2.add("geschickt");
        row2.add("https://de.linkedin.com/jobs/view/java-c-programmer-java-full-stack-engineer-at-falcon-smart-it-falconsmartit-4025506418?trk=public_jobs_topcard-title");

        List<String> row3 = new ArrayList<>();
        row3.add("SAP");
        row3.add("Intern (f/m/d) - Java Developer for Cloud Applications");
        row3.add("Walldorf, Baden-Württemberg, Germany");
        row3.add(formattedDate);
        row3.add("geschickt");
        row3.add("https://de.linkedin.com/jobs/view/intern-f-m-d-java-developer-for-cloud-applications-at-sap-3826036379?trk=public_jobs_topcard-title");

        List<String> row4 = new ArrayList<>();
        row4.add("Skywaves Rise");
        row4.add("Java Developer");
        row4.add("Frankfurt Rhine-Main Metropolitan Area");
        row4.add(formattedDate);
        row4.add("geschickt");
        row4.add("https://de.linkedin.com/jobs/view/java-developer-at-skywaves-rise-4027234674?trk=public_jobs_topcard-title");

        List<String> row5 = new ArrayList<>();
        row5.add("TESTQ Technologies Limited");
        row5.add("Java Full Stack Engineer (Java / C Programmer)");
        row5.add("Leipzig, Saxony, Germany");
        row5.add(formattedDate);
        row5.add("geschickt");
        row5.add("https://de.linkedin.com/jobs/view/java-full-stack-engineer-java-c-programmer-at-testq-technologies-limited-4027258161?trk=public_jobs_topcard-title");

        List<List<String>> data = List.of(row1, row2, row3, row4, row5);

        LocalDateTime now = LocalDateTime.now();

        // Определяем форматтер с нужным форматом
        DateTimeFormatter formatterSheet = DateTimeFormatter.ofPattern("dd.MM.yyyy HH-mm");
        String formattedDateTime = now.format(formatterSheet);

        ExcelWriter writer = new ExcelWriter(data, headers, "C:\\Users\\gluza\\OneDrive\\Рабочий стол\\jobs.xlsx", formattedDateTime);
        writer.write();


    }
}