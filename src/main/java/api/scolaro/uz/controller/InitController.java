package api.scolaro.uz.controller;

import api.scolaro.uz.dto.university.UniversityCreateDTO;
import api.scolaro.uz.enums.UniversityDegreeType;
import api.scolaro.uz.service.UniversityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Deprecated
@RequiredArgsConstructor
@RestController
@RequestMapping("/init/data")
public class InitController {

    @Autowired
    private UniversityService universityService;

    @GetMapping("/start")
    public void initUniversity(@RequestParam(value = "key" , required = true) String key) {
        if (!key.equals("!a3(sdgdamdq.ewr,!a")) {
            return;
        }

        List<UniversityDegreeType> degreeTypeList = new LinkedList<>();
        degreeTypeList.add(UniversityDegreeType.Bachelor);
        degreeTypeList.add(UniversityDegreeType.MasterDegree);
        degreeTypeList.add(UniversityDegreeType.Doctorate);


        // 1. OXFORD
        UniversityCreateDTO university1 = new UniversityCreateDTO();
        university1.setWebSite("www.ox.ac.uk");
        university1.setDescription("<p>The University of Oxford is the oldest university in the English-speaking world and the world&rsquo;s second oldest surviving university. While its exact founding date is unknown, there is evidence that teaching took place as far back as 1096.</p>\n" +
                "<p>Located in and around Oxford&rsquo;s medieval city centre, the university comprises 44 colleges and halls, and over 100 libraries, making it the largest library system in the UK.</p>\n" +
                "<p>Students number around 22,000 in total, just over half of whom are undergraduates while over 40 per cent are international, representing 140 countries between them.</p>\n" +
                "<p>Called the \"city of dreaming spires\" by Victorian poet, Matthew Arnold, Oxford has the youngest population of any city in England and Wales: nearly a quarter of its residents are university students, which gives Oxford a noticeable buzz.</p>\n" +
                "<p>Oxford has an alumni network of over 250,000 individuals, including more than 120 Olympic medallists, 26 Nobel Prize winners, seven poets laureate, and over 30 modern world leaders (Bill Clinton, Aung San Suu Kyi, Indira Gandhi and 26 UK Prime Ministers, among them).</p>\n" +
                "<p>The university is associated with 11 winners of the Nobel Prize in chemistry, five in physics and 16 in medicine. Notable Oxford thinkers and scientists include Tim Berners-Lee, Stephen Hawking and Richard Dawkins. The actors Hugh Grant and Rosamund Pike also went to Oxford, as did the writers Oscar Wilde, Graham Greene, Vikram Seth and Philip Pullman.</p>\n" +
                "<p>Oxford&rsquo;s first international student, named Emo of Friesland, was enrolled in 1190, while the modern day university prides itself on having an &lsquo;international character&rsquo; with connections to almost every country in the world and 40% of its faculty drawn from overseas.</p>\n" +
                "<p>As a modern, research-driven university, Oxford has numerous strengths but cites particular prowess in the sciences, having recently ranked number one in the world for medicine (if its medical sciences division was a university in its own right, it would be the fourth largest in the UK) and among the top ten universities globally for life sciences, physical sciences, social sciences, and the arts and humanities.</p>");
        university1.setName("University of Oxford");
        university1.setRating(1L);
        university1.setCountryId(6L);
        university1.setPhotoId("sc8d85e53-acde-4ca7-be39-5e794b4257e0");
        university1.setLogoId("sc8d85e53-acde-4ca7-be39-5e794b4257e0_logo");
        university1.setDegreeList(degreeTypeList);

        List<String> facultyList1 = new LinkedList<>();
        facultyList1.add("fb31520b-21c4-4012-b074-2e68d01a0a86");
        facultyList1.add("e9baf081-98bf-44d9-956c-2e53303cfffc");
        facultyList1.add("76949a37-2d3b-4ce6-b4ab-9f67f1b561f3");
        facultyList1.add("72917341-78f1-4c76-a391-a14c63ad2c2e");
        facultyList1.add("02da017b-ecd7-4bb0-b56d-e2959933c374");
        facultyList1.add("5f9af5d7-db40-468f-9c66-e064d1479cc4");
        facultyList1.add("8939c2aa-ba01-4659-85c4-3af00499662e");
        facultyList1.add("b06db058-5db1-4581-a262-44047322677d");
        facultyList1.add("ce027fa4-2ad2-449a-8faa-64e962668493");
        facultyList1.add("1aaa73fc-9227-4c8e-9580-b214a21ae37d");
        facultyList1.add("5f44e8aa-608f-400f-bddf-d24360fb092d");
        facultyList1.add("b37f126a-f9fd-4bb4-8c48-8500483b411a");
        facultyList1.add("3e7f543c-a53f-40b2-a0c8-5c63a78e0d60");
        facultyList1.add("ae45281b-5a87-4dfc-a717-a7341a3e55f7");
        facultyList1.add("35ea02d7-bbbf-4fa9-b2bf-414158706254");
        facultyList1.add("dc2d02fc-ce4c-4102-bb7b-277ba03dc7d2");
        facultyList1.add("f28e53f6-f510-417a-9a46-387a97bd4142");
        facultyList1.add("655829ec-f0bd-43ad-ac6f-e899f4cb275c");
        //business favulties
        facultyList1.add("846e13da-a586-4521-804b-e5d2800b5896");
        facultyList1.add("2a0f19d7-bce5-4cfb-8c75-5012adefa8dd");
        facultyList1.add("4e3f7d0c-de9d-4d34-9d64-8e0662e698a0");
        facultyList1.add("2542382b-deb0-4c3b-9506-4854d4fac916");
        facultyList1.add("bd776090-d7ee-46fb-8435-9e5d6eb673ba");
        facultyList1.add("0869bb07-2252-43f5-8041-51daff6bca41");
        facultyList1.add("dbca9992-5878-4e34-8772-962d19c8deb6");
        facultyList1.add("5fab9d1d-e6cb-4e72-8e4b-93996d17dbde");
        facultyList1.add("e1c6cd2c-48d0-4e1a-9f77-16be04ffefae");
        facultyList1.add("d4538b6e-5935-4f21-9101-7a60139a0561");
        facultyList1.add("2df80be6-871a-4727-8228-370b935b8eca");
        facultyList1.add("cdef7d59-1bb3-4715-b53e-afb9bd2bd81e");
        facultyList1.add("db971772-055f-4272-86de-efdcb3583527");
        facultyList1.add("1eb11b0f-6b34-4973-bda7-b20dd2540073");
        facultyList1.add("e8cba975-0992-4879-b6b8-0367dba2724f");
        facultyList1.add("efec9ff9-a8a1-489d-a0ad-843ddcc8f8dc");
        facultyList1.add("22bf7776-282f-4c57-a861-ebe2354d1099");
        facultyList1.add("d3e8d0a1-8116-46d0-852d-f848a67944d4");
        facultyList1.add("1abd7454-ba9f-4569-ae13-451158030ecf");
        facultyList1.add("825e0804-4d44-4742-ad1e-bde1cf278973");
        facultyList1.add("10c4e7c9-8496-4449-a240-79acbc463078");
        facultyList1.add("2f97a4d9-5812-4840-94f1-609f6ba91f4d");
        facultyList1.add("505fbda5-c592-47bd-8480-27d269516d50");
        facultyList1.add("67c495ee-655d-4393-b5d3-00f316a751c1");
        facultyList1.add("2502f63b-3f0a-40b1-9025-015493f83308");
        facultyList1.add("a548445e-4297-48fc-94ad-2a6f289d227c");
        facultyList1.add("ef0b91da-1fe3-45de-bfe7-282158cd9b9c");
        facultyList1.add("0cdc6486-271b-45e4-b517-988407ad6e0e");
        facultyList1.add("67b73605-bd51-4774-8657-6cf71be426e5");
        facultyList1.add("08284360-7d6f-4b85-a599-2f7ef1424a35");
        facultyList1.add("a0b210cc-a7db-41b2-8842-470683c94406");
        facultyList1.add("6177c87b-b552-4b55-8a52-d416ec37e22b");
        facultyList1.add("fb5c6ce3-ca1c-4028-871a-24d7f918f3be");
        facultyList1.add("eb7dde8a-2c44-4d99-b5d5-c66a3dfc6803");
        facultyList1.add("a3096239-bbd1-4b23-b2e2-ad6029f46c6e");
        facultyList1.add("95fdefd9-65ac-48d9-a0aa-32673a7e1fb4");
        facultyList1.add("f0febd67-e4dc-4055-b7be-7beb7587ff5c");
        facultyList1.add("a67a5a6b-e113-46f0-90d1-5e45ed231ac0");
        facultyList1.add("c94c7d91-b47d-4dfa-b478-c42f72521ea0");
        facultyList1.add("4f1ed038-a928-44b8-bcfd-c6089a8f0157");
        facultyList1.add("c0c17f05-325d-49ff-9661-0759698b9e37");
        facultyList1.add("caa5de66-07ff-404c-85ef-0162069aa4c8");
        facultyList1.add("7933edfc-92dd-49ae-8e23-163bf2707e3c");
        university1.setFacultyList(facultyList1);

        universityService.create(university1);

        // 2. Harvard
        UniversityCreateDTO university2 = new UniversityCreateDTO();
        university2.setWebSite("www.harvard.edu");
        university2.setDescription("<p>Dating back to 1636, Harvard University is the oldest university in the US and is regarded as one of the most prestigious in the world.</p>\n" +
                "<p>It was named after its first benefactor, John Harvard, who left his library and half his estate to the institution when he died in 1638.</p>\n" +
                "<p>The private Ivy League institution has connections to more than 45 Nobel laureates, over 30 heads of state and 48 Pulitzer prizewinners. It has more than 323,000 living alumni, including over 271,000 in the US and nearly 52,000 in 201 other countries. Thirteen US presidents have honorary degrees from the institution; the most recent of these was awarded to John F. Kennedy in 1956.</p>\n" +
                "<p>Faculty members who have been awarded a Nobel prize in recent years include chemist Martin Karplus and economist Alvin Roth, while notable alumni who were given the honour include former US vice-president Al Gore, who won the Peace Prize in 2007, and poet Seamus Heaney, who was a professor at Harvard from 1981 to 1997.</p>\n" +
                "<p>Situated in Cambridge, Massachusetts, Harvard&rsquo;s 5,000-acre campus houses 12 degree-granting schools in addition to the Radcliffe Institute for Advanced Study, two theatres and five museums. It is also home to the largest academic library in the world, with 20.4 million volumes, 180,000 serial titles, an estimated 400 million manuscript items, 10 million photographs, 124 million archived web pages and 5.4 terabytes of born-digital archives and manuscripts.</p>\n" +
                "<p>There are more than 400 student organisations on campus, and Harvard&rsquo;s medical school is connected to 10 hospitals.</p>\n" +
                "<p>The university receives one of the largest financial endowments of any higher education institution in the world; it created $1.5 billion in the fiscal year ended June 2013 &ndash; more than a third of Harvard&rsquo;s total operating revenue in that year.</p>\n" +
                "<p>Harvard&rsquo;s official colour is crimson, following a vote in 1910, after two student rowers provided crimson scarves to their teammates so that spectators could differentiate the university&rsquo;s team during a regatta in 1858.</p>");
        university2.setName("Harvard University");
        university2.setRating(2L);
        university2.setCountryId(2L);
        university2.setPhotoId("c5ad27c3-6430-4130-a141-9f8dfcaf1f1d");
        university2.setLogoId("c5ad27c3-6430-4130-a141-9f8dfcaf1f1d_logo");
        university2.setDegreeList(degreeTypeList);
        List<String> facultyList2 = new LinkedList<>();
        //Humanities
        facultyList2.add("0fbfc47b-48d2-4337-a60d-32776fbf7fd5");
        facultyList2.add("385d253d-f949-434e-bef5-1d8db8ac2f4f");
        facultyList2.add("8aa164bd-a557-48ac-a26b-8abb0e66eadb");
        facultyList2.add("a1437996-4a98-4e9e-a190-16cfab1a1723");
        facultyList2.add("0532a184-f39c-4823-8b4c-2dad2e1a9053");
        facultyList2.add("6d9afd2c-87be-44a1-9c94-0d32108c3e2c");
        facultyList2.add("16818f9b-254b-4d0d-9561-928aaa0f7d06");
        facultyList2.add("4737df7c-a108-4459-9623-7d0026d5f40b");
        facultyList2.add("ce4f80e9-4613-46a9-aec9-7f565c07baec");
        facultyList2.add("936cdac3-5c8c-4ab9-bc15-a64608540d07");
        facultyList2.add("d5c3be71-4faa-4073-b259-c65d7daf3ffc");
        facultyList2.add("602b762f-6d10-4f02-8e9d-6ab854e22651");
        facultyList2.add("7dd914b3-69b6-41d2-a7fb-9a01e7c9d231");
        facultyList2.add("25867f21-aa5a-47fc-afd6-e51d1af3a06a");
        facultyList2.add("e262b667-d5e2-4393-9d71-54dd3e30f5bb");
        facultyList2.add("38ecdc7a-4920-4e9e-a19c-27cb3ef118fe");
        facultyList2.add("6aadc300-6a4b-4d14-bcfe-bdd035b3b488");
        facultyList2.add("e6d34ff5-b0da-45bd-9b9f-ffcc8ef17882");
        facultyList2.add("e2cef3b9-723b-4ca7-82a1-d7ddf53a7d68");
        facultyList2.add("aa610645-0ad3-41dc-98fc-fa999801a44f");
        facultyList2.add("502c3b7f-f995-46a5-966e-a063601a6657");
        facultyList2.add("c46794c3-b107-4350-88f2-3cc41f4c41fe");
        facultyList2.add("7115d554-74bb-4b48-9696-9f1c2031363d");
        facultyList2.add("11586f1b-13f2-4e8f-93e2-b9d69c922cbf");
        facultyList2.add("dca8e373-56b5-476f-b2f8-d04674fbcff2");
        facultyList2.add("e8596004-45ca-415b-b3fc-555fd719e3ab");
        facultyList2.add("57f9abb7-372d-4e99-986e-3300a5d0f014");
        facultyList2.add("76831345-99db-48a9-b2ee-b6f6790c2456");
        facultyList2.add("a87d40e9-1eea-4d0f-b187-a2e03781fb99");
        //language faculties
        facultyList2.add("8d7f739d-cc08-41a4-b838-db94b1ac679b");
        facultyList2.add("dd7a65d7-0c69-4bb6-a114-67e372ec030f");
        facultyList2.add("b4aa1e41-c2f0-4d6c-870b-f7d653763a52");
        facultyList2.add("777163b7-5562-4e83-a902-bac62806a37e");
        facultyList2.add("029bbf6f-2c88-4005-8bf6-e3f5d8997cd8");
        facultyList2.add("14bed110-886f-45d5-9a20-21682eb79df2");
        facultyList2.add("6b1e61d5-3e68-4d3c-937b-af8c8e6735c2");
        facultyList2.add("f34cbf7c-f6ff-4458-b82f-9d96b1da3acd");
        facultyList2.add("079e7917-fcdc-43ff-9068-d829654e9a0c");
        facultyList2.add("11b78ffb-eb50-4c9e-bf8a-c6550aec85a3");
        facultyList2.add("6cf5037c-4543-4a60-aea1-ee1bdda3c0c5");
        facultyList2.add("4edf27a0-8140-4c4c-981d-569c575cbbf9");
        facultyList2.add("9923759e-e943-46ee-b053-9f009f8098b9");
        facultyList2.add("dc10c10c-84db-4e88-8f04-ea38ff904362");
        facultyList2.add("4acdba7f-61f1-4bef-9b58-3714d3ad10be");
        facultyList2.add("912edfb4-1173-44a4-9721-0e4b124a0bcb");
        facultyList2.add("ba2999d0-7c95-446a-b7a3-2037e862d390");
        facultyList2.add("428d5bf1-d724-4894-a07a-bba22bddbb58");
        facultyList2.add("463e52b3-1342-4c8f-8983-6724b08ee0ab");
        facultyList2.add("04a46a8d-cd88-486d-80cb-47393ef0f431");

        university2.setFacultyList(facultyList2);
        universityService.create(university2);

        // 3. Cambridge

        UniversityCreateDTO university3 = new UniversityCreateDTO();
        university3.setName("University of Cambridge");
        university3.setWebSite("www.cam.ac.uk");
        university3.setDescription("<p>Founded in 1209, the University of Cambridge is a collegiate public research institution. Its 800-year history makes it the fourth-oldest surviving university in the world and the second-oldest university in the English-speaking world.</p>\n" +
                "<p>Cambridge serves more than 18,000 students from all cultures and corners of the world. Nearly 4,000 of its students are international and hail from over 120 different countries. In addition, the university&rsquo;s International Summer Schools offer 150 courses to students from more than 50 countries.</p>\n" +
                "<p>The university is split into 31 autonomous colleges where students receive small group teaching sessions known as college supervisions.&nbsp;</p>\n" +
                "<p>Six schools are spread across the university&rsquo;s colleges, housing roughly 150 faculties and other institutions. The six schools are: Arts and Humanities, Biological Sciences, Clinical Medicine, Humanities and Social Sciences, Physical Sciences and Technology.</p>\n" +
                "<p>The campus is located in the centre of the city of Cambridge, with its numerous listed buildings and many of the older colleges situated on or near the river Cam.</p>\n" +
                "<p>The university is home to over 100 libraries, which, between them, hold more than 15 million books in total. In the main Cambridge University library alone, which is a legal depository, there are eight million holdings. The university also owns nine arts, scientific and cultural museums that are open to the public throughout the year, as well as a botanical garden.</p>\n" +
                "<p>Cambridge University Press is a non-school institution and operates as the university&rsquo;s publishing business. With over 50 offices worldwide, its publishing list is made up of 45,000 titles spanning academic research, professional development, research journals, education and bible publishing.</p>\n" +
                "<p>In total, 92 affiliates of the university have been awarded Nobel Prizes, covering every category.&nbsp;</p>\n" +
                "<p>The university&rsquo;s endowment is valued at nearly &pound;6 billion.</p>");
        university3.setRating(3L);
        university3.setCountryId(6L);
        university3.setPhotoId("9646de43-ec49-415a-858b-ea34f5b05a52");
        university3.setLogoId("9646de43-ec49-415a-858b-ea34f5b05a52_logo");
        university3.setDegreeList(degreeTypeList);
        List<String> facultyList3 = new LinkedList<>();
        facultyList3.add("846e13da-a586-4521-804b-e5d2800b5896");
        facultyList3.add("2a0f19d7-bce5-4cfb-8c75-5012adefa8dd");
        facultyList3.add("4e3f7d0c-de9d-4d34-9d64-=8e0662e698a0");
        facultyList3.add("2542382b-deb0-4c3b-9506-4854d4fac916");
        facultyList3.add("bd776090-d7ee-46fb-8435-9e5d6eb673ba");
        facultyList3.add("0869bb07-2252-43f5-8041-51daff6bca41");
        facultyList3.add("dbca9992-5878-4e34-8772-962d19c8deb6");
        facultyList3.add("5fab9d1d-e6cb-4e72-8e4b-93996d17dbde");
        facultyList3.add("e1c6cd2c-48d0-4e1a-9f77-16be04ffefae");
        facultyList3.add("d4538b6e-5935-4f21-9101-7a60139a0561");
        facultyList3.add("2df80be6-871a-4727-8228-370b935b8eca");
        facultyList3.add("cdef7d59-1bb3-4715-b53e-afb9bd2bd81e");
        facultyList3.add("db971772-055f-4272-86de-efdcb3583527");
        facultyList3.add("1eb11b0f-6b34-4973-bda7-b20dd2540073");
        facultyList3.add("e8cba975-0992-4879-b6b8-0367dba2724f");
        facultyList3.add("efec9ff9-a8a1-489d-a0ad-843ddcc8f8dc");
        facultyList3.add("22bf7776-282f-4c57-a861-ebe2354d1099");
        facultyList3.add("d3e8d0a1-8116-46d0-852d-f848a67944d4");
        facultyList3.add("1abd7454-ba9f-4569-ae13-451158030ecf");
        facultyList3.add("825e0804-4d44-4742-ad1e-bde1cf278973");
        facultyList3.add("10c4e7c9-8496-4449-a240-79acbc463078");
        facultyList3.add("2f97a4d9-5812-4840-94f1-609f6ba91f4d");
        facultyList3.add("505fbda5-c592-47bd-8480-27d269516d50");
        facultyList3.add("67c495ee-655d-4393-b5d3-00f316a751c1");
        facultyList3.add("2502f63b-3f0a-40b1-9025-015493f83308");
        facultyList3.add("a548445e-4297-48fc-94ad-2a6f289d227c");
        facultyList3.add("ef0b91da-1fe3-45de-bfe7-282158cd9b9c");
        facultyList3.add("0cdc6486-271b-45e4-b517-988407ad6e0e");
        facultyList3.add("67b73605-bd51-4774-8657-6cf71be426e5");
        facultyList3.add("08284360-7d6f-4b85-a599-2f7ef1424a35");
        facultyList3.add("a0b210cc-a7db-41b2-8842-470683c94406");
        facultyList3.add("6177c87b-b552-4b55-8a52-d416ec37e22b");
        facultyList3.add("fb5c6ce3-ca1c-4028-871a-24d7f918f3be");
        facultyList3.add("eb7dde8a-2c44-4d99-b5d5-c66a3dfc6803");
        facultyList3.add("a3096239-bbd1-4b23-b2e2-ad6029f46c6e");
        facultyList3.add("95fdefd9-65ac-48d9-a0aa-32673a7e1fb4");
        facultyList3.add("f0febd67-e4dc-4055-b7be-7beb7587ff5c");
        facultyList3.add("a67a5a6b-e113-46f0-90d1-5e45ed231ac0");
        facultyList3.add("c94c7d91-b47d-4dfa-b478-c42f72521ea0");
        facultyList3.add("4f1ed038-a928-44b8-bcfd-c6089a8f0157");
        facultyList3.add("c0c17f05-325d-49ff-9661-0759698b9e37");
        facultyList3.add("caa5de66-07ff-404c-85ef-0162069aa4c8");
        facultyList3.add("7933edfc-92dd-49ae-8e23-163bf2707e3c");
        //Education faculties
        facultyList3.add("9a9e94fc-19e5-4fd3-b2e8-645f669faddb");
        facultyList3.add("b3c69f2d-348e-4486-b965-68b43e46846f");
        facultyList3.add("c0690064-fd33-4f0b-904a-6b1d5f06ac43");
        facultyList3.add("bf6434cb-3be4-469b-bd51-8dbc2c7c3793");
        facultyList3.add("c9f3f8d9-4660-4937-bc83-55cba005bf45");
        facultyList3.add("f307dba3-ba7f-4a73-8620-b6bb2bd267ff");
        facultyList3.add("0984fee1-2ecd-4ca2-8dd7-9c6b88386861");
        facultyList3.add("b2ca5c9e-ea7b-4c2f-91e1-b8de8d81c69a");
        facultyList3.add("44ceda40-86c9-4504-b53e-fbf238456288");
        facultyList3.add("226143e3-2850-4025-9438-1e77b884a000");
        facultyList3.add("770f49df-c44a-4a87-a00e-534006ffd9d6");
        facultyList3.add("da4ed86c-6681-47c9-aaef-5e22bbb0266d");
        facultyList3.add("b89f98de-830f-4281-8096-6c368ad39856");
        facultyList3.add("bb00cc68-a1e8-4211-a460-c726661c08df");
        facultyList3.add("f43af208-8c0f-40cc-8df5-2eebc9c1c946");
        facultyList3.add("e6fd4130-7f1d-4318-a8ac-8177da1627fd");
        facultyList3.add("e9111cb4-9828-4d54-8753-14aca93c4e3e");
        facultyList3.add("91ca349d-3a13-4882-8ae1-cbd74a8abf71");
        facultyList3.add("59ce1713-e2d6-460a-8124-f0a7f709247d");
        facultyList3.add("fe499ab4-ab8f-4429-9f9a-3219e87cec1f");
        facultyList3.add("5493754d-6d3f-4cbd-bea0-8b6990bce9bd");
        facultyList3.add("d0ee832b-170b-4980-ac86-41112b4a5f45");
        facultyList3.add("236a6d50-345c-4a6a-8961-2861b27f084d");
        facultyList3.add("6091ecd8-340f-4b46-81f4-2f34a7bfa177");
        facultyList3.add("f550bcf1-4eca-49b8-b539-05684beb6882");
        facultyList3.add("977c2254-0d11-4c2f-81f2-620350181c11");
        facultyList3.add("b39c362f-c87d-49c6-a39b-1ae1a45030ef");
        university3.setFacultyList(facultyList3);
        universityService.create(university3);

        // 4.Stanford
        UniversityCreateDTO university4 = new UniversityCreateDTO();
        university4.setName("Stanford University");
        university4.setWebSite("www.stanford.edu");
        university4.setDescription("<div id=\"rmjs-1\" aria-expanded=\"true\" data-readmore=\"\" data-js-read-more=\"height-650\">\n" +
                "<p>Stanford University has one of the largest campuses in the US and is one of the most prestigious universities in the world.</p>\n" +
                "<p>It was established in 1885 and opened six years later as a co-educational and non-denominational private institution.</p>\n" +
                "<p>Its location, less than an hour&rsquo;s drive south of San Francisco next to Palo Alto, is in the heart of California&rsquo;s Silicon Valley, and the university is known for its entrepreneurial spirit.</p>\n" +
                "<p>This entrepreneurialism has its roots in the aftermath of the Second World War, when the provost encouraged innovation, resulting in a self-sufficient industry that would become Silicon Valley.</p>\n" +
                "<p>By 1970, the university had a linear accelerator and hosted part of the early network that would become the technical foundation of the internet.</p>\n" +
                "<p>The main campus spans 8,180 acres and is home to almost all the undergraduates who study at the university.</p>\n" +
                "<p>There are 700 major university buildings housing 40 departments within the three academic schools and four professional schools, alongside 18 independent laboratories, centres and institutes.</p>\n" +
                "<p>Stanford counts 21 Nobel laureates within its community, and numerous famous alumni associated with the university from the worlds of business, politics, media, sport and technology.</p>\n" +
                "<p>The 31st&nbsp;president of the US, Herbert Hoover, was part of the very first class at Stanford, and received a degree in geology in 1895. Currently, Stanford is also one of the leading producers of US Congress members.</p>\n" +
                "<p>The alumni include 30 living billionaires, 17 astronauts, 18 Turing Award recipients and two Fields Medallists.</p>\n" +
                "<p>Google&rsquo;s co-founders met at Stanford while pursuing doctorate degrees, although neither ultimately completed their theses.</p>\n" +
                "<p>In total, companies founded by Stanford affiliates and alumni generate more than $2.7 trillion annual revenue, which would be the 10th&nbsp;largest economy in the world. These companies include Nike, Netflix, Hewlett-Packard, Sun Microsystems, Instagram, Snapchat, PayPal and Yahoo.</p>\n" +
                "<p>The first American woman to go into space, Sally Ride, received an undergraduate degree in physics from Stanford in 1973. Just 10 years later, she made her ascent into space.</p>\n" +
                "<p>In the five years leading up to 2012, the university embarked on a challenge to raise more than $4 billion. The fundraising exceeded this target and concluded the campaign having raised $6.2 billion, which will be used for more faculty appointments, graduate research fellowships and scholarships, and construction on 38 new or existing campus buildings.</p>\n" +
                "<p>Some of the funds have already been used for large projects, including the world&rsquo;s largest dedicated stem cell research facility, a new business school campus, a law school expansion, a new Engineering Quad, a campus concert hall and an art museum.</p>\n" +
                "<p>Unofficially, the Stanford motto is a German quotation &ldquo;<em>Die Luft der Freiheit weht</em>&rdquo;, which translates as &ldquo;the wind of freedom blows&rdquo;.</p>\n" +
                "</div>\n" +
                "<p><a href=\"https://www.timeshighereducation.com/world-university-rankings/stanford-university\" aria-controls=\"rmjs-1\" data-readmore-toggle=\"rmjs-1\">&nbsp;</a></p>");
        university4.setRating(4L);
        university4.setCountryId(2L);
        university4.setPhotoId("872224bc-6717-48a0-a833-8febea3d8287");
        university4.setLogoId("872224bc-6717-48a0-a833-8febea3d8287_logo");
        university4.setDegreeList(degreeTypeList);
        List<String> facultyList4 = new LinkedList<>();
        //health faculty
        facultyList4.add("8dd31a73-1ab2-403d-b889-e22d2a99fbec");
        facultyList4.add("1400f76a-cdf5-4c4f-a117-d7ba75a600f2");
        facultyList4.add("4f80da53-5f22-4fcb-a0d1-64094dff581a");
        facultyList4.add("b2f0bd87-95c6-402b-884b-55d993a88945");
        facultyList4.add("d52a9e76-03cb-42b4-8a88-409e3c1c94f6");
        facultyList4.add("e76b4c80-0d9a-4e62-aaa7-772a2bd502c8");
        facultyList4.add("40901d76-3aba-44f6-9d8b-e1189bbc67f9");
        facultyList4.add("b529957b-c722-47b2-97c6-b42e5f98e286");
        facultyList4.add("da295505-a4d2-45ef-8304-bc1d2aa138e5");
        facultyList4.add("d622a717-e898-448a-a290-2e5f2ff7fc52");
        facultyList4.add("bddadc1c-24d6-4bbd-8d44-ec913c4462dc");
        facultyList4.add("d6d673d0-5e2c-45fb-b81b-6a94e1e6d4cb");
        facultyList4.add("234b744c-26d8-4094-87e5-7cf6e03cedb1");
        facultyList4.add("f3adddf0-b444-4941-8165-335226565445");
        facultyList4.add("b8b97142-9bb0-4eb6-a47a-b9cb8fcd689a");
        facultyList4.add("e22b3909-ce40-47cf-8b54-b0a706a2df21");
        facultyList4.add("9d9355aa-1730-4474-8389-3eccfcdd1fd9");
        facultyList4.add("8e041211-bcda-458c-90fb-96a0c1d4ccb1");
        facultyList4.add("868648b7-3f49-42ae-b63e-9f2513c2ff85");
        facultyList4.add("835157b4-1670-4122-88ec-488031bc2a5d");
        facultyList4.add("e2552301-56f6-4b17-aaed-dfa05a8e0966");
        facultyList4.add("8ef85093-7c7b-4996-9c8a-c0552a604706");
        facultyList4.add("e10efe65-ab28-440c-9795-b1000de57fab");
        facultyList4.add("1e23ed42-8116-468b-8f77-f4492b87f5f8");
        facultyList4.add("01d4d64f-a0a1-41c2-aad2-6cfb065dc36b");
        facultyList4.add("34e732be-d867-4ed7-b3b2-dd8b57ab7f9f");
        facultyList4.add("db5a9d55-e705-4f9b-95e7-d4a94205a678");
        facultyList4.add("102d5d17-ae9e-4139-ac3b-53476d5d096e");
        facultyList4.add("2b8c5319-3e99-4732-9023-787491b8bb93");
        facultyList4.add("e4ff8e2f-521b-4d6d-889c-a4e69cdf6967");
        facultyList4.add("342309d9-464a-4446-a33b-74bd4e21aaa4");
        facultyList4.add("6c19e270-89e1-492f-9144-5d6a2493ceb3");
        //Natural science faculties
        facultyList4.add("a42c3401-d82f-464c-8054-ad0449b92e8a");
        facultyList4.add("e146c49f-2633-4ca8-87b1-4eccf7b5f9f4");
        facultyList4.add("5ec62766-9129-4432-a881-dbe2be4a0b12");
        facultyList4.add("f17ad858-0935-4ccb-bd1d-ddb3a9d6950c");
        facultyList4.add("4db2f039-c576-4fa2-a08a-ebb5ddd62307");
        facultyList4.add("0cfa779a-97f4-445a-b9b8-44ec21f63e08");
        facultyList4.add("bb737b92-9a67-4a4b-a6a0-883489780ae2");
        facultyList4.add("f5f65c48-4587-4908-b790-481273fc231e");
        facultyList4.add("c993863e-946c-422c-a3c2-7e2af1414d23");
        facultyList4.add("62a4265e-e13f-4690-8fd7-ce3b9b545e20");
        facultyList4.add("ccb0734d-bacd-4b03-8f90-2b810101fa56");
        facultyList4.add("b2a94998-57b8-42bd-85c5-04573ffeeae4");
        facultyList4.add("08faf75a-9e76-406c-80ac-71c895129696");
        facultyList4.add("c8465d31-db8b-46f4-9a42-f1af9f4250f7");
        facultyList4.add("a6ac494b-45cf-4b79-bd81-8cdac14b71db");
        facultyList4.add("fdfe87ee-d240-49c2-9970-6c88b5c74a35");
        facultyList4.add("97f9110f-254e-4394-b42c-259c45aa0f5e");
        facultyList4.add("66f7908a-f801-4a60-bd43-6e3041aa1f0d");
        facultyList4.add("1fa4c796-85e1-4495-bfe0-dbffe04fea97");
        facultyList4.add("b7813475-4128-4e8d-940e-af9a706810c5");
        facultyList4.add("0ee51720-6b79-4651-bd04-87a7d990d966");
        facultyList4.add("93c7ab56-730f-40fd-adcf-4ac384ec3750");
        facultyList4.add("34f285b8-b23b-420c-9994-eaaf4c54ce9d");
        facultyList4.add("df275283-534b-403f-a702-8e6c84459d96");
        facultyList4.add("d07a2f29-1752-4a37-ba48-206bcc6b0d60");
        facultyList4.add("b38804ae-2977-412f-9e1b-6c91be968afa");
        facultyList4.add("5372847f-5b27-41a1-9f10-baa024d2e51b");
        facultyList4.add("30e3813f-6780-477a-b720-218956e3eb90");
        facultyList4.add("fa41bbf7-05f5-45d2-894b-00b9b7f1eb8e");
        facultyList4.add("daa31b19-8078-4f25-bac1-e580c372d65d");
        university4.setFacultyList(facultyList4);
        universityService.create(university4);

        //5. MIT

        UniversityCreateDTO university5 = new UniversityCreateDTO();
        university5.setName("Massachusetts Institute of Technology");
        university5.setWebSite("www.mit.edu");
        university5.setDescription("<div id=\"rmjs-1\" aria-expanded=\"true\" data-readmore=\"\" data-js-read-more=\"height-650\">\n" +
                "<p>The Massachusetts Institute of Technology (MIT) is an independent, coeducational, private research university based in the city of Cambridge, Massachusetts.</p>\n" +
                "<p>Established in 1861, MIT aims to &lsquo;further knowledge and prepare students in science, technology and other fields of study that will best benefit the nation and the world today&rsquo;. Its motto is Mens et Manus, which translates as &ldquo;Mind and Hand&rdquo;.</p>\n" +
                "<p>The university lays claim to 85 Nobel Laureates, 58 National Medal of Science winners, 29 National Medal of Technology and Innovation winners and 45 MacArthur Fellows. Among its impressive alumni is Kofi Annan, former secretary-general of the United Nations.</p>\n" +
                "<p>Scientific discoveries and technological advances accredited to MIT include the first chemical synthesis of penicillin, the development of radar, the discovery of quarks, and the invention of magnetic core memory, which enabled the development of digital computers.</p>\n" +
                "<p>MIT is currently organised into five different schools: architecture and planning, engineering, humanities, arts and social sciences, management and science.</p>\n" +
                "<p>It is home to around 1,000 faculty members and over 11,000 undergraduate and graduate students. MIT&rsquo;s current areas of research include digital learning, sustainable energy, Big Data, human health and much more.</p>\n" +
                "<p>In addition to its emphasis on innovation and entrepreneurship, MIT also boasts a diverse and vibrant campus environment with a wide array of student groups. The campus is arranged over 168 acres within Cambridge, and features 18 student residences, 26 acres of playing fields, 20 gardens and green-space areas, as well as over 100 public works of art.</p>\n" +
                "<p>MIT estimates that all its living alumni have between them launched more than 30,000 active companies, created 4.6 million jobs and generated roughly $1.9 trillion in annual revenue.</p>\n" +
                "<p>Taken together, this &lsquo;MIT Nation&rsquo; is equivalent, they say, to the 10th-largest economy in the world.</p>\n" +
                "</div>");

        university5.setRating(5L);
        university5.setCountryId(2L);
        university5.setPhotoId("08e00b15-c802-419a-bb29-d7049df09ee8");
        university5.setLogoId("08e00b15-c802-419a-bb29-d7049df09ee8_logo");
        university5.setDegreeList(degreeTypeList);
        List<String> facultyList5 = new LinkedList<>();
        //Technology faculties
        facultyList5.add("e70f0839-e171-4909-9acb-9a797b8aef85");
        facultyList5.add("4204782a-f2b0-4077-a8bb-944b18f28e87");
        facultyList5.add("3559cc0d-494d-407f-aba6-3f52de4d9d9a");
        facultyList5.add("0611290f-bb76-4ff3-a469-81cf6fd64fac");
        facultyList5.add("94e25c0d-b16f-4a9a-9cda-02b5125fca12");
        facultyList5.add("3aa5e673-bea0-497d-b575-f3def6f9dddf");
        facultyList5.add("8d10af46-6e7a-4083-9f4a-6bd2c4f2b25b");
        facultyList5.add("5b52dce5-eeda-4c67-8db9-c92815a4f81e");
        facultyList5.add("e7e961f0-5281-4525-9a91-79ebf28eeae8");
        facultyList5.add("f579fbd9-1c6c-4d62-b664-6f0c96df6117");
        facultyList5.add("7d0412d5-d93a-421a-8c14-23a034ba3feb");
        facultyList5.add("8d8ba26a-92d6-4a35-8947-8a3559346339");
        facultyList5.add("847bf777-6f1d-44b8-b60f-b6c7b94d3a1c");
        facultyList5.add("9dfdb35b-0bf5-4df1-b60e-8e5d91a1a97c");
        facultyList5.add("cae143a4-7a39-41a3-8db3-c3a128bbc359");
        facultyList5.add("adf7698b-cd15-4538-9f7a-e95336c6a4b1");
        facultyList5.add("1ea51528-d714-4e8b-ac47-11d0ae487185");
        facultyList5.add("cea2ea0d-32eb-4feb-aa5f-88450c6d3603");
        facultyList5.add("2c24fdef-ee88-4e38-ae8c-f3abb65aaa39");
        facultyList5.add("875174d3-2a3d-41ad-b411-18621ce046fc");
        facultyList5.add("e86ff9f9-86a0-4ae7-9c54-70711c1f355b");
        facultyList5.add("991d38a4-d0b6-4dad-b4ff-2c304336bb58");
        facultyList5.add("54933852-6788-4934-9488-5894a2cc0b54");
        facultyList5.add("47090b7e-eda9-4e10-9d58-ab04bd5fa0aa");
        facultyList5.add("66defbf0-a763-4d84-84b5-f759b04d9b36");
        facultyList5.add("a044be33-a551-4ba3-ab7a-5f196fe55984");
        facultyList5.add("600e2beb-f0b4-42e5-b89a-60ddaedd0135");
        facultyList5.add("8c8660b0-b92d-45f7-81c2-09751c51bb50");
        facultyList5.add("227a6ddd-b221-4250-a4f6-a5fee58b085f");
        facultyList5.add("3225bfbc-eaf7-4b00-b8f8-475a1ecb30ca");
        facultyList5.add("9d868d71-4784-4668-947c-bccee9539b95");
        facultyList5.add("f830b1b0-d45f-44b4-849a-ea72c2b560fc");
        //Physical Sciences
        facultyList5.add("ebb0674d-60b7-48ea-bab5-03fd6fd7ec9d");
        facultyList5.add("47249de0-384b-4fb1-a55f-b5d800fbd676");
        facultyList5.add("211a1334-1ada-45b3-86be-ef1301bdde33");
        facultyList5.add("cb5ae418-7cf3-4925-927d-ba69bc442cbf");
        facultyList5.add("41afbbe1-aa34-421f-80d3-bc9e0a077361");
        facultyList5.add("e2e6bbd6-37bc-4cfd-a4a1-65935aef7a7b");
        facultyList5.add("3a6019be-9ad0-4b04-ac14-257df64208db");
        facultyList5.add("349ddeec-fc73-4004-a254-ebdb842b8f66");
        facultyList5.add("d35b8488-a70c-4b4f-86af-f889112e6184");
        facultyList5.add("6b606102-87f4-44d9-97b2-ddebcb0861d6");
        facultyList5.add("54275e85-0d96-4623-b68f-8bc38598761c");
        facultyList5.add("88fe4c0f-272e-488e-b20a-cbe4b7859c9d");
        facultyList5.add("641fb142-b650-4d3a-9f01-f9de7f57cea5");
        facultyList5.add("5be74340-c3da-487a-8c16-6aeb9a25c1f7");
        facultyList5.add("8cd55cc4-360c-4530-9e49-a1052b065d43");
        facultyList5.add("067a2a51-b5d2-4d92-ae1f-6568ea49f410");
        facultyList5.add("f5700727-72e7-4804-9f4f-b6ffb7ce98f9");
        facultyList5.add("c36005a7-6dfc-44b0-82fa-c1bf50aa8a24");
        facultyList5.add("ee7c71a3-48ce-42b1-b782-408bb7bd6794");
        facultyList5.add("a5c424f6-762f-4187-9f91-cadc32445611");
        facultyList5.add("23eee949-7f8c-471c-af12-63e8e76d530c");
        facultyList5.add("27d34c29-b132-451f-82ea-fdcc3dd83a2b");
        facultyList5.add("5c9e1167-a38b-4d72-967c-9b1d51c6879e");
        university5.setFacultyList(facultyList5);
        universityService.create(university5);

        // 6. California Institute of technology
        UniversityCreateDTO university6 = new UniversityCreateDTO();
        university6.setName("California Institute of Technology");
        university6.setWebSite("www.caltech.edu");
        university6.setDescription("<div id=\"rmjs-1\" aria-expanded=\"true\" data-readmore=\"\" data-js-read-more=\"height-650\">\n" +
                "<p>The California Institute of Technology (Caltech) is a world-renowned science and engineering research and education institution, where extraordinary faculty and students seek answers to complex questions, discover new knowledge, lead innovation, and transform the future.</p>\n" +
                "<p>Caltech has six academic divisions with a strong emphasis in science and technology teaching and research. The university has a competitive admissions process ensuring that only a small number of the most gifted students are admitted.</p>\n" +
                "<p>Caltech has a high research output and alongside many high-quality facilities, both on campus and globally. This includes the Jet Propulsion Laboratory, the Caltech Seismological Laboratory and the International Observatory Network.</p>\n" +
                "<p>The alumni and faculty of Caltech have been awarded 39 Nobel Prizes, one Fields Medal, six Turing Awards and 71 United States National Medal of Science or Technology. Four chief scientists of the US Air Force have also attended the institution.</p>\n" +
                "<p>The campus is located in Pasadena, California, approximately 11 km away from downtown Los Angeles. The school's official mascot is a beaver, paying tribute to nature's engineer.</p>\n" +
                "<p>Caltech students are also well-known for playing pranks, with one of the most famous pranks including changing the \"Hollywood\" sign to read Caltech, by covering up parts of the letters.</p>\n" +
                "</div>");

        university6.setRating(6L);
        university6.setCountryId(2L);
        university6.setPhotoId("0a9c9655-89d8-4da5-89bd-3bdafd684c42");
        university6.setLogoId("0a9c9655-89d8-4da5-89bd-3bdafd684c42_logo");
        university6.setDegreeList(degreeTypeList);
        List<String> facultyList6 = new LinkedList<>();
        //Technology faculties
        facultyList6.add("e70f0839-e171-4909-9acb-9a797b8aef85");
        facultyList6.add("4204782a-f2b0-4077-a8bb-944b18f28e87");
        facultyList6.add("3559cc0d-494d-407f-aba6-3f52de4d9d9a");
        facultyList6.add("0611290f-bb76-4ff3-a469-81cf6fd64fac");
        facultyList6.add("94e25c0d-b16f-4a9a-9cda-02b5125fca12");
        facultyList6.add("3aa5e673-bea0-497d-b575-f3def6f9dddf");
        facultyList6.add("8d10af46-6e7a-4083-9f4a-6bd2c4f2b25b");
        facultyList6.add("5b52dce5-eeda-4c67-8db9-c92815a4f81e");
        facultyList6.add("e7e961f0-5281-4525-9a91-79ebf28eeae8");
        facultyList6.add("f579fbd9-1c6c-4d62-b664-6f0c96df6117");
        facultyList6.add("7d0412d5-d93a-421a-8c14-23a034ba3feb");
        facultyList6.add("8d8ba26a-92d6-4a35-8947-8a3559346339");
        facultyList6.add("847bf777-6f1d-44b8-b60f-b6c7b94d3a1c");
        facultyList6.add("9dfdb35b-0bf5-4df1-b60e-8e5d91a1a97c");
        facultyList6.add("cae143a4-7a39-41a3-8db3-c3a128bbc359");
        facultyList6.add("adf7698b-cd15-4538-9f7a-e95336c6a4b1");
        facultyList6.add("1ea51528-d714-4e8b-ac47-11d0ae487185");
        facultyList6.add("cea2ea0d-32eb-4feb-aa5f-88450c6d3603");
        facultyList6.add("2c24fdef-ee88-4e38-ae8c-f3abb65aaa39");
        facultyList6.add("875174d3-2a3d-41ad-b411-18621ce046fc");
        facultyList6.add("e86ff9f9-86a0-4ae7-9c54-70711c1f355b");
        facultyList6.add("991d38a4-d0b6-4dad-b4ff-2c304336bb58");
        facultyList6.add("54933852-6788-4934-9488-5894a2cc0b54");
        facultyList6.add("47090b7e-eda9-4e10-9d58-ab04bd5fa0aa");
        facultyList6.add("66defbf0-a763-4d84-84b5-f759b04d9b36");
        facultyList6.add("a044be33-a551-4ba3-ab7a-5f196fe55984");
        facultyList6.add("600e2beb-f0b4-42e5-b89a-60ddaedd0135");
        facultyList6.add("8c8660b0-b92d-45f7-81c2-09751c51bb50");
        facultyList6.add("227a6ddd-b221-4250-a4f6-a5fee58b085f");
        facultyList6.add("3225bfbc-eaf7-4b00-b8f8-475a1ecb30ca");
        facultyList6.add("9d868d71-4784-4668-947c-bccee9539b95");
        facultyList6.add("f830b1b0-d45f-44b4-849a-ea72c2b560fc");
        //Physical Sciences
        facultyList6.add("ebb0674d-60b7-48ea-bab5-03fd6fd7ec9d");
        facultyList6.add("47249de0-384b-4fb1-a55f-b5d800fbd676");
        facultyList6.add("211a1334-1ada-45b3-86be-ef1301bdde33");
        facultyList6.add("cb5ae418-7cf3-4925-927d-ba69bc442cbf");
        facultyList6.add("41afbbe1-aa34-421f-80d3-bc9e0a077361");
        facultyList6.add("e2e6bbd6-37bc-4cfd-a4a1-65935aef7a7b");
        facultyList6.add("3a6019be-9ad0-4b04-ac14-257df64208db");
        facultyList6.add("349ddeec-fc73-4004-a254-ebdb842b8f66");
        facultyList6.add("d35b8488-a70c-4b4f-86af-f889112e6184");
        facultyList6.add("6b606102-87f4-44d9-97b2-ddebcb0861d6");
        facultyList6.add("54275e85-0d96-4623-b68f-8bc38598761c");
        facultyList6.add("88fe4c0f-272e-488e-b20a-cbe4b7859c9d");
        facultyList6.add("641fb142-b650-4d3a-9f01-f9de7f57cea5");
        facultyList6.add("5be74340-c3da-487a-8c16-6aeb9a25c1f7");
        facultyList6.add("8cd55cc4-360c-4530-9e49-a1052b065d43");
        facultyList6.add("067a2a51-b5d2-4d92-ae1f-6568ea49f410");
        facultyList6.add("f5700727-72e7-4804-9f4f-b6ffb7ce98f9");
        facultyList6.add("c36005a7-6dfc-44b0-82fa-c1bf50aa8a24");
        facultyList6.add("ee7c71a3-48ce-42b1-b782-408bb7bd6794");
        facultyList6.add("a5c424f6-762f-4187-9f91-cadc32445611");
        facultyList6.add("23eee949-7f8c-471c-af12-63e8e76d530c");
        facultyList6.add("27d34c29-b132-451f-82ea-fdcc3dd83a2b");
        facultyList6.add("5c9e1167-a38b-4d72-967c-9b1d51c6879e");
        university6.setFacultyList(facultyList6);
        universityService.create(university6);
        //7.  Princeton University
        UniversityCreateDTO university7 = new UniversityCreateDTO();
        university7.setName("Princeton University");
        university7.setWebSite("www.princeton.edu");
        university7.setDescription("<div id=\"rmjs-1\" aria-expanded=\"true\" data-readmore=\"\" data-js-read-more=\"height-650\">\n" +
                "<p>Princeton is one of the oldest universities in the US and is regarded as one of the world&rsquo;s most illustrious higher education institutions.</p>\n" +
                "<p>Founded in 1746 as the College of New Jersey, it was officially renamed Princeton University in 1896 in honour of the area where it is based, opening its famous graduate school in 1900.</p>\n" +
                "<p>Acclaimed for its commitment to teaching, the Ivy League institution offers residential accommodation to all of its undergraduates across all four years of study, with 98 per cent of undergraduates living on campus.</p>\n" +
                "<p>Its student body is relatively small, with fewer than 10,000 in total, and international students make up 12 per cent of undergraduates.</p>\n" +
                "<p>Princeton is also one of the world&rsquo;s foremost research universities with connections to more than 40 Nobel laureates, 17 winners of the National Medal of Science and five recipients of the National Humanities Medal.</p>\n" +
                "<p>Faculty members who have been awarded a Nobel prize in recent years include chemists Tomas Lindahl and Osamu Shimomura, economists Paul Krugman and Angus Deaton and physicists Arthur McDonald and David Gross.</p>\n" +
                "<p>Notable alumni who have won a Nobel prize include the physicists Richard Feynman and Robert Hofstadter and chemists Richard Smalley and Edwin McMillan.</p>\n" +
                "<p>Princeton has also educated two US presidents, James Madison and Woodrow Wilson, who was also the university&rsquo;s president prior to entering the White House. Other distinguished graduates include Michelle Obama, actors Jimmy Stewart and Brooke Shields, Amazon founder Jeff Bezos and Apollo astronaut Pete Conrad.</p>\n" +
                "<p>Princeton, which is consistently ranked among the world&rsquo;s top 10 universities, is renowned for its campus&rsquo; park-like beauty as well as some of its landmark buildings, designed by some of America&rsquo;s most well-known architects. For instance, its Lewis Library was designed by Frank Gehry and contains many of the university&rsquo;s science collections. Its McCarter Theatre Center has won a Tony Award for the best regional theatre in the country.</p>\n" +
                "<p>Spread across 500 acres, the Princeton campus has about 180 buildings, including 10 libraries containing about 14 million holdings. It is popular with visitors, with about 800,000 people visiting its open campus each year, generating about $2 billion in revenue.</p>\n" +
                "<p>The Princeton area, which has a population of about 30,000 residents, is also something of a destination itself, with many attracted by its tree-lined streets and wide variety of shops, restaurants and parks.</p>\n" +
                "<p>The university is within easy reach of both New York City and Philadelphia, with the &ldquo;Dinky&rdquo; shuttle train providing a regular service lasting about one hour to both cities. Princeton regularly subsidises many student trips to concerts, plays and athletic events in the two cities.</p>\n" +
                "</div>");

        university7.setRating(7L);
        university7.setCountryId(2L);
        university7.setPhotoId("3df88cb6-6d77-498b-8a19-49cf1f7fac62");
        university7.setLogoId("3df88cb6-6d77-498b-8a19-49cf1f7fac62_logo");
        university7.setDegreeList(degreeTypeList);
        List<String> facultyList7 = new LinkedList<>();
        //Engineering faculties
        facultyList7.add("15dbabfd-03cc-4c39-8338-c51ecb2cb067");
        facultyList7.add("2fd141ef-eeac-4b96-898b-d54616c38bb0");
        facultyList7.add("4434fede-9fbf-40b2-b534-1f4d62490e68");
        facultyList7.add("95e1a138-b9f7-4dba-863d-651750fb39ed");
        facultyList7.add("b488fe9b-c9f1-4e14-a2f2-6bf332a510db");
        facultyList7.add("c51e3fe9-361d-4bfc-bac8-bc9bc7525447");
        facultyList7.add("c4c72036-7a6f-4c17-920e-dbce1f30f95c");
        facultyList7.add("e43add4a-681b-42bc-aa1d-a1ddea9f4dd5");
        facultyList7.add("40d3a7aa-bf07-47f4-9014-0de2b6dc0987");
        facultyList7.add("8f06da4e-3c8a-47ef-82b7-707a5b31c85e");
        facultyList7.add("9320c3de-30f6-4710-bfc1-305f97c6becd");
        facultyList7.add("cc67eff8-21c1-436b-acbe-2aee209b9efd");
        facultyList7.add("82f9cb05-5ea5-43ac-9a47-1762cca99f2b");
        facultyList7.add("1c26339e-7a31-4c02-8af1-2e39aa4de3ff");
        facultyList7.add("95c99573-6a3f-4e00-bf4b-62cfdd9d9b1c");
        facultyList7.add("473e7e4f-dfb1-42e2-94de-4d4153c9504a");
        facultyList7.add("c197517d-62d0-44f9-b5d6-9944af3e3263");
        facultyList7.add("dddc26e8-132c-46e6-a3f1-34c3ccbe5b07");
        facultyList7.add("56fe280b-dc86-4ade-a22f-9199aa318acd");
        facultyList7.add("c7b3a687-b29b-4875-9bbc-9524c4dd16f7");
        facultyList7.add("e16a2589-09d9-4e43-806f-07b857f219a0");
        facultyList7.add("88c611c2-6620-4179-b9b2-799c1b61dba6");
        facultyList7.add("d197eed4-f75e-4cbc-87e7-936482fa42aa");
        facultyList7.add("d3935edd-fa20-4eeb-ac96-134d878895c9");
        facultyList7.add("f1a6ab92-f2d8-4c77-a1f0-534b0852596b");
        facultyList7.add("96a4f1d4-b0e1-4917-8cef-6cfad6ca7e1a");
        facultyList7.add("783b1c0c-ff80-4c58-a961-a9625273422c");
        facultyList7.add("0f665aec-b99e-4d1f-a9a8-4dd81c32e087");
        facultyList7.add("17c636f0-f223-4d82-a512-6e8273ef50c5");
        facultyList7.add("371a7885-31a9-4f17-b80e-dbdc4913977e");
        //Natural science faculties
        facultyList7.add("a42c3401-d82f-464c-8054-ad0449b92e8a");
        facultyList7.add("e146c49f-2633-4ca8-87b1-4eccf7b5f9f4");
        facultyList7.add("5ec62766-9129-4432-a881-dbe2be4a0b12");
        facultyList7.add("f17ad858-0935-4ccb-bd1d-ddb3a9d6950c");
        facultyList7.add("4db2f039-c576-4fa2-a08a-ebb5ddd62307");
        facultyList7.add("0cfa779a-97f4-445a-b9b8-44ec21f63e08");
        facultyList7.add("bb737b92-9a67-4a4b-a6a0-883489780ae2");
        facultyList7.add("f5f65c48-4587-4908-b790-481273fc231e");
        facultyList7.add("c993863e-946c-422c-a3c2-7e2af1414d23");
        facultyList7.add("62a4265e-e13f-4690-8fd7-ce3b9b545e20");
        facultyList7.add("ccb0734d-bacd-4b03-8f90-2b810101fa56");
        facultyList7.add("b2a94998-57b8-42bd-85c5-04573ffeeae4");
        facultyList7.add("08faf75a-9e76-406c-80ac-71c895129696");
        facultyList7.add("c8465d31-db8b-46f4-9a42-f1af9f4250f7");
        facultyList7.add("a6ac494b-45cf-4b79-bd81-8cdac14b71db");
        facultyList7.add("fdfe87ee-d240-49c2-9970-6c88b5c74a35");
        facultyList7.add("97f9110f-254e-4394-b42c-259c45aa0f5e");
        facultyList7.add("66f7908a-f801-4a60-bd43-6e3041aa1f0d");
        facultyList7.add("1fa4c796-85e1-4495-bfe0-dbffe04fea97");
        facultyList7.add("b7813475-4128-4e8d-940e-af9a706810c5");
        facultyList7.add("0ee51720-6b79-4651-bd04-87a7d990d966");
        facultyList7.add("93c7ab56-730f-40fd-adcf-4ac384ec3750");
        facultyList7.add("34f285b8-b23b-420c-9994-eaaf4c54ce9d");
        facultyList7.add("df275283-534b-403f-a702-8e6c84459d96");
        facultyList7.add("d07a2f29-1752-4a37-ba48-206bcc6b0d60");
        facultyList7.add("b38804ae-2977-412f-9e1b-6c91be968afa");
        facultyList7.add("5372847f-5b27-41a1-9f10-baa024d2e51b");
        facultyList7.add("30e3813f-6780-477a-b720-218956e3eb90");
        facultyList7.add("fa41bbf7-05f5-45d2-894b-00b9b7f1eb8e");
        facultyList7.add("daa31b19-8078-4f25-bac1-e580c372d65d");
        university7.setFacultyList(facultyList7);
        universityService.create(university7);

        //8.University of California, Berkeley
        UniversityCreateDTO university8 = new UniversityCreateDTO();
        university8.setName("University of California, Berkeley");
        university8.setWebSite("www.berkeley.edu");
        university8.setDescription("<div id=\"rmjs-1\" aria-expanded=\"true\" data-readmore=\"\" data-js-read-more=\"height-650\">\n" +
                "<p>The University of California, Berkeley, a public research university, is regarded as one of the most prestigious state universities in the US. Part of the University of California System, it was founded in 1868.</p>\n" +
                "<p>Berkeley&rsquo;s creation stemmed from a vision in the state constitution of a university that would &ldquo;contribute even more than California&rsquo;s gold to the glory and happiness of advancing generations&rdquo;.</p>\n" +
                "<p>Berkeley&rsquo;s colours of blue and gold were chosen in 1873 &ndash; blue representing not just the California sky and ocean but also the Yale graduates who helped to found the institution; gold the &ldquo;Golden State&rdquo; of California.</p>\n" +
                "<p>The university is located in San Francisco&rsquo;s Bay Area, where it is home to about 27,000 undergraduate students and 10,000 postgraduate students.</p>\n" +
                "<p>Berkeley faculty have won 19 Nobel prizes, mostly in physics, chemistry and economics. Recent winners include Saul Perlmutter, who won the 2011 Nobel Prize for Physics for leading a team that discovered the accelerating expansion of the universe, suggesting the existence of a form of dark energy that comprises 75 per cent of the universe; and George Akerlof, who won the 2001 Prize for Economics for demonstrating how markets malfunction when buyers and sellers have access to different information.</p>\n" +
                "<p>Notable alumni include novelist and journalist Jack London, Oscar-winning actor Gregory Peck, former prime minister and president of Pakistan Zulfikar Ali Bhutto, author Joan Didion and Women&rsquo;s World Cup-winning US footballer Alex Morgan.</p>\n" +
                "<p>Berkeley has a tradition as a centre of political activism. During the 1960s and 1970s, the campus was a hotbed for student protests against the Vietnam War.</p>\n" +
                "<p>Attractions on campus include a Botanic Garden established in 1890 and the 60,000-capacity California Memorial Stadium used by the university&rsquo;s sports teams.</p>\n" +
                "<p>The Golden Bear is the symbol of Berkeley&rsquo;s sports teams.</p>\n" +
                "<p>Berkeley&rsquo;s sporting prowess was demonstrated at the 2012 Olympics in London, when its graduates won 17 medals &ndash; 11 gold, one silver and five bronze. If Berkeley had been a country, it would have ranked joint sixth in the gold medal table, alongside France and Germany.</p>\n" +
                "</div>");

        university8.setRating(8L);
        university8.setCountryId(2L);
        university8.setPhotoId("91ff240b-0491-4013-a505-aaf21d4394cc");
        university8.setLogoId("91ff240b-0491-4013-a505-aaf21d4394cc_logo");
        university8.setDegreeList(degreeTypeList);
        List<String> facultyList8 = new LinkedList<>();
        //health faculty
        facultyList8.add("8dd31a73-1ab2-403d-b889-e22d2a99fbec");
        facultyList8.add("1400f76a-cdf5-4c4f-a117-d7ba75a600f2");
        facultyList8.add("4f80da53-5f22-4fcb-a0d1-64094dff581a");
        facultyList8.add("b2f0bd87-95c6-402b-884b-55d993a88945");
        facultyList8.add("d52a9e76-03cb-42b4-8a88-409e3c1c94f6");
        facultyList8.add("e76b4c80-0d9a-4e62-aaa7-772a2bd502c8");
        facultyList8.add("40901d76-3aba-44f6-9d8b-e1189bbc67f9");
        facultyList8.add("b529957b-c722-47b2-97c6-b42e5f98e286");
        facultyList8.add("da295505-a4d2-45ef-8304-bc1d2aa138e5");
        facultyList8.add("d622a717-e898-448a-a290-2e5f2ff7fc52");
        facultyList8.add("bddadc1c-24d6-4bbd-8d44-ec913c4462dc");
        facultyList8.add("d6d673d0-5e2c-45fb-b81b-6a94e1e6d4cb");
        facultyList8.add("234b744c-26d8-4094-87e5-7cf6e03cedb1");
        facultyList8.add("f3adddf0-b444-4941-8165-335226565445");
        facultyList8.add("b8b97142-9bb0-4eb6-a47a-b9cb8fcd689a");
        facultyList8.add("e22b3909-ce40-47cf-8b54-b0a706a2df21");
        facultyList8.add("9d9355aa-1730-4474-8389-3eccfcdd1fd9");
        facultyList8.add("8e041211-bcda-458c-90fb-96a0c1d4ccb1");
        facultyList8.add("868648b7-3f49-42ae-b63e-9f2513c2ff85");
        facultyList8.add("835157b4-1670-4122-88ec-488031bc2a5d");
        facultyList8.add("e2552301-56f6-4b17-aaed-dfa05a8e0966");
        facultyList8.add("8ef85093-7c7b-4996-9c8a-c0552a604706");
        facultyList8.add("e10efe65-ab28-440c-9795-b1000de57fab");
        facultyList8.add("1e23ed42-8116-468b-8f77-f4492b87f5f8");
        facultyList8.add("01d4d64f-a0a1-41c2-aad2-6cfb065dc36b");
        facultyList8.add("34e732be-d867-4ed7-b3b2-dd8b57ab7f9f");
        facultyList8.add("db5a9d55-e705-4f9b-95e7-d4a94205a678");
        facultyList8.add("102d5d17-ae9e-4139-ac3b-53476d5d096e");
        facultyList8.add("2b8c5319-3e99-4732-9023-787491b8bb93");
        facultyList8.add("e4ff8e2f-521b-4d6d-889c-a4e69cdf6967");
        facultyList8.add("342309d9-464a-4446-a33b-74bd4e21aaa4");
        facultyList8.add("6c19e270-89e1-492f-9144-5d6a2493ceb3");
        //Transportation & Logistics
        facultyList8.add("6293ed8b-7b4a-43a1-8cad-811edb2ec564");
        facultyList8.add("5b89dce1-d345-4bb1-8ddf-8a00e28038bd");
        facultyList8.add("7c5adfa9-bdf1-4ab8-9fee-fd672817e026");
        facultyList8.add("f7f87ea3-799d-42f3-bd34-f1f406b5de53");
        facultyList8.add("879c85e3-a834-4e3e-8841-6afb06cd7423");
        facultyList8.add("79c098a1-89c5-48dc-8c5a-c4d9d3240f7b");
        facultyList8.add("2d853122-cacc-416f-b10c-aa0762e48b04");
        facultyList8.add("f7349b59-c315-4756-a9d8-67a9be45a892");
        facultyList8.add("bfd51f5b-327d-4b44-8631-db9ce286f6fc");
        facultyList8.add("c2206c46-500c-4994-b430-c2308c1fd1f0");
        facultyList8.add("98152f85-db7c-4c3a-9541-5800be78c39e");
        facultyList8.add("631f132c-278c-4e60-8481-32827e295b98");
        facultyList8.add("7c236d8b-8b10-446f-9f10-e7c704ee7f45");
        facultyList8.add("2864353f-05e6-45c8-a795-95c28374bab2");
        facultyList8.add("59c38745-b5b6-4b3e-aa51-4635e2fb5a67");
        facultyList8.add("575a16ad-1794-4d60-9b8b-a81b962c70f1");
        facultyList8.add("3b88f23e-0138-4f04-9765-fee2228406bd");
        university8.setFacultyList(facultyList8);
        universityService.create(university8);

        //9 . Yale university

        UniversityCreateDTO university9 = new UniversityCreateDTO();
        university9.setName("Yale University");
        university9.setWebSite("www.yale.edu");
        university9.setDescription("<div id=\"rmjs-1\" aria-expanded=\"true\" data-readmore=\"\" data-js-read-more=\"height-650\">\n" +
                "<p>Yale University is a private Ivy League research university which is the third-oldest higher education institution in the US.</p>\n" +
                "<p>Yale traces its history back to 1701, when it was founded as the Collegiate School in Saybrook, Connecticut, which moved to New Haven 15 years later.</p>\n" +
                "<p>In 1718 it was renamed Yale College, in honour of Welsh benefactor Elihu Yale, and it was the first university in the US to award a PhD, in 1861.</p>\n" +
                "<p>Yale&rsquo;s central campus covers 260 acres of New Haven, and includes buildings dating back to the mid-18th century.</p>\n" +
                "<p>The university is made up of 14 schools, and students follow a liberal arts curriculum, covering humanities and arts, sciences and social sciences before choosing a departmental major. Students also receive instruction in writing skills, quantitative reasoning and foreign languages.</p>\n" +
                "<p>Unusually for the US, Yale students are housed in residential colleges on the model of the universities of Oxford and Cambridge. There are 12 historic colleges, and construction of two more started in 2014.</p>\n" +
                "<p>Around one in five students is international, and more than half of all undergraduates receive scholarships or grants from the university.</p>\n" +
                "<p>Yale has an endowment that exceeds $25 billion (&pound;17.3 billion), making it the second-richest educational institution in the world, and a library that holds more than 15 million volumes, making it the third-largest in the US.</p>\n" +
                "<p>Yale alumni and sports teams are known as &ldquo;Bulldogs&rdquo;, and many Yale graduates have gone on to notable careers in politics, the arts and science.</p>\n" +
                "<p>Four Yale graduates signed the American Declaration of Independence, and the university has educated five US presidents: William Howard Taft, Gerald Ford, George H. W. Bush, Bill Clinton and George W. Bush. Twenty Yale alumni have won Nobel prizes, including economist Paul Krugman, while 32 have won the Pulitzer Prize.</p>\n" +
                "<p>Other notable alumni include US secretaries of state Hillary Clinton and John Kerry, and actress Meryl Streep.</p>\n" +
                "<p>Yale&rsquo;s campus includes many famous buildings, such as the Beinecke Rare Book &amp; Manuscript Library, the Peabody Museum of Natural History and the Sterling Memorial Library.</p>\n" +
                "<p>New Haven is a city of about 130,000 people, located two and a half hours south of Boston, and an hour and a half north of New York. It has many shops, museums and restaurants, and is close to beaches, hiking trails and historic attractions.</p>\n" +
                "</div>");

        university9.setRating(9L);
        university9.setCountryId(2L);
        university9.setPhotoId("b1b12487-f340-4247-9516-53d980dd3814");
        university9.setLogoId("b1b12487-f340-4247-9516-53d980dd3814_logo");
        university9.setDegreeList(degreeTypeList);
        List<String> facultyList9 = new LinkedList<>();
        //Architecture
        facultyList9.add("fb31520b-21c4-4012-b074-2e68d01a0a86");
        facultyList9.add("e9baf081-98bf-44d9-956c-2e53303cfffc");
        facultyList9.add("76949a37-2d3b-4ce6-b4ab-9f67f1b561f3");
        facultyList9.add("72917341-78f1-4c76-a391-a14c63ad2c2e");
        facultyList9.add("02da017b-ecd7-4bb0-b56d-e2959933c374");
        facultyList9.add("5f9af5d7-db40-468f-9c66-e064d1479cc4");
        facultyList9.add("8939c2aa-ba01-4659-85c4-3af00499662e");
        facultyList9.add("b06db058-5db1-4581-a262-44047322677d");
        facultyList9.add("ce027fa4-2ad2-449a-8faa-64e962668493");
        facultyList9.add("1aaa73fc-9227-4c8e-9580-b214a21ae37d");
        facultyList9.add("5f44e8aa-608f-400f-bddf-d24360fb092d");
        facultyList9.add("b37f126a-f9fd-4bb4-8c48-8500483b411a");
        facultyList9.add("3e7f543c-a53f-40b2-a0c8-5c63a78e0d60");
        facultyList9.add("ae45281b-5a87-4dfc-a717-a7341a3e55f7");
        facultyList9.add("35ea02d7-bbbf-4fa9-b2bf-414158706254");
        facultyList9.add("dc2d02fc-ce4c-4102-bb7b-277ba03dc7d2");
        facultyList9.add("f28e53f6-f510-417a-9a46-387a97bd4142");
        facultyList9.add("655829ec-f0bd-43ad-ac6f-e899f4cb275c");
        //education faculties
        facultyList9.add("9a9e94fc-19e5-4fd3-b2e8-645f669faddb");
        facultyList9.add("b3c69f2d-348e-4486-b965-68b43e46846f");
        facultyList9.add("c0690064-fd33-4f0b-904a-6b1d5f06ac43");
        facultyList9.add("bf6434cb-3be4-469b-bd51-8dbc2c7c3793");
        facultyList9.add("c9f3f8d9-4660-4937-bc83-55cba005bf45");
        facultyList9.add("f307dba3-ba7f-4a73-8620-b6bb2bd267ff");
        facultyList9.add("0984fee1-2ecd-4ca2-8dd7-9c6b88386861");
        facultyList9.add("b2ca5c9e-ea7b-4c2f-91e1-b8de8d81c69a");
        facultyList9.add("44ceda40-86c9-4504-b53e-fbf238456288");
        facultyList9.add("226143e3-2850-4025-9438-1e77b884a000");
        facultyList9.add("770f49df-c44a-4a87-a00e-534006ffd9d6");
        facultyList9.add("da4ed86c-6681-47c9-aaef-5e22bbb0266d");
        facultyList9.add("b89f98de-830f-4281-8096-6c368ad39856");
        facultyList9.add("bb00cc68-a1e8-4211-a460-c726661c08df");
        facultyList9.add("f43af208-8c0f-40cc-8df5-2eebc9c1c946");
        facultyList9.add("e6fd4130-7f1d-4318-a8ac-8177da1627fd");
        facultyList9.add("e9111cb4-9828-4d54-8753-14aca93c4e3e");
        facultyList9.add("91ca349d-3a13-4882-8ae1-cbd74a8abf71");
        facultyList9.add("59ce1713-e2d6-460a-8124-f0a7f709247d");
        facultyList9.add("fe499ab4-ab8f-4429-9f9a-3219e87cec1f");
        facultyList9.add("5493754d-6d3f-4cbd-bea0-8b6990bce9bd");
        facultyList9.add("d0ee832b-170b-4980-ac86-41112b4a5f45");
        facultyList9.add("236a6d50-345c-4a6a-8961-2861b27f084d");
        facultyList9.add("6091ecd8-340f-4b46-81f4-2f34a7bfa177");
        facultyList9.add("f550bcf1-4eca-49b8-b539-05684beb6882");
        facultyList9.add("977c2254-0d11-4c2f-81f2-620350181c11");
        facultyList9.add("b39c362f-c87d-49c6-a39b-1ae1a45030ef");

        university9.setFacultyList(facultyList9);
        universityService.create(university9);


        // 10. Imperial College London
        UniversityCreateDTO university10 = new UniversityCreateDTO();
        university10.setName("Imperial College London");
        university10.setWebSite("www.imperial.ac.uk");
        university10.setDescription("<div id=\"rmjs-1\" aria-expanded=\"true\" data-readmore=\"\" data-js-read-more=\"height-650\">\n" +
                "<p>Imperial College London, a science-based institution based in the centre of the capital, is regarded as one of the UK&rsquo;s leading institutions.</p>\n" +
                "<p>The college has around 15,000 students and 8,000 staff, with a focus on four main areas: science, engineering, medicine and business.</p>\n" +
                "<p>The institution has its roots in the vision of Prince Albert to make London&rsquo;s South Kensington a centre for education, with colleges going alongside the nearby Natural History Museum, Victoria and Albert Museum and Science Museum.</p>\n" +
                "<p>Imperial was granted its charter in 1907, merging the Royal College of Science, the Royal School of Mines and the City &amp; Guilds College.</p>\n" +
                "<p>The institution boasts 14 Nobel Prize winners, including Sir Alexander Fleming, the discoverer of penicillin.</p>\n" +
                "<p>Famous alumni include science fiction author H.G. Wells, Queen guitarist Brian May, former prime minister of India Rajiv Gandhi, former UK chief medical officer Sir Liam Donaldson, and former chief executive of Singapore Airlines Chew Choon Seng.</p>\n" +
                "<p>The college&rsquo;s motto is&nbsp;<em>Scientia imperii decus et tutamen</em>, which translates as &ldquo;Scientific knowledge, the crowning glory and the safeguard of the empire&rdquo;.</p>\n" +
                "<p>Imperial&rsquo;s most notable landmark is the Queen's Tower, a remainder of the Imperial Institute, built to mark Queen Victoria's Golden Jubilee in 1887.</p>\n" +
                "</div>");

        university10.setRating(10L);
        university10.setCountryId(6L);
        university10.setPhotoId("4a80e23e-16be-4870-ba1c-3e0800b7fbf2");
        university10.setLogoId("4a80e23e-16be-4870-ba1c-3e0800b7fbf2_logo");
        university10.setDegreeList(degreeTypeList);
        List<String> facultyList10 = new LinkedList<>();
        //Natural science faculties
        facultyList10.add("a42c3401-d82f-464c-8054-ad0449b92e8a");
        facultyList10.add("e146c49f-2633-4ca8-87b1-4eccf7b5f9f4");
        facultyList10.add("5ec62766-9129-4432-a881-dbe2be4a0b12");
        facultyList10.add("f17ad858-0935-4ccb-bd1d-ddb3a9d6950c");
        facultyList10.add("4db2f039-c576-4fa2-a08a-ebb5ddd62307");
        facultyList10.add("0cfa779a-97f4-445a-b9b8-44ec21f63e08");
        facultyList10.add("bb737b92-9a67-4a4b-a6a0-883489780ae2");
        facultyList10.add("f5f65c48-4587-4908-b790-481273fc231e");
        facultyList10.add("c993863e-946c-422c-a3c2-7e2af1414d23");
        facultyList10.add("62a4265e-e13f-4690-8fd7-ce3b9b545e20");
        facultyList10.add("ccb0734d-bacd-4b03-8f90-2b810101fa56");
        facultyList10.add("b2a94998-57b8-42bd-85c5-04573ffeeae4");
        facultyList10.add("08faf75a-9e76-406c-80ac-71c895129696");
        facultyList10.add("c8465d31-db8b-46f4-9a42-f1af9f4250f7");
        facultyList10.add("a6ac494b-45cf-4b79-bd81-8cdac14b71db");
        facultyList10.add("fdfe87ee-d240-49c2-9970-6c88b5c74a35");
        facultyList10.add("97f9110f-254e-4394-b42c-259c45aa0f5e");
        facultyList10.add("66f7908a-f801-4a60-bd43-6e3041aa1f0d");
        facultyList10.add("1fa4c796-85e1-4495-bfe0-dbffe04fea97");
        facultyList10.add("b7813475-4128-4e8d-940e-af9a706810c5");
        facultyList10.add("0ee51720-6b79-4651-bd04-87a7d990d966");
        facultyList10.add("93c7ab56-730f-40fd-adcf-4ac384ec3750");
        facultyList10.add("34f285b8-b23b-420c-9994-eaaf4c54ce9d");
        facultyList10.add("df275283-534b-403f-a702-8e6c84459d96");
        facultyList10.add("d07a2f29-1752-4a37-ba48-206bcc6b0d60");
        facultyList10.add("b38804ae-2977-412f-9e1b-6c91be968afa");
        facultyList10.add("5372847f-5b27-41a1-9f10-baa024d2e51b");
        facultyList10.add("30e3813f-6780-477a-b720-218956e3eb90");
        facultyList10.add("fa41bbf7-05f5-45d2-894b-00b9b7f1eb8e");
        facultyList10.add("daa31b19-8078-4f25-bac1-e580c372d65d");
        //General Studies
        facultyList10.add("7bd2efde-13a5-4f2f-b488-2945ea161192");
        facultyList10.add("99ff1500-b874-4685-9aef-56fd5fef8489");
        facultyList10.add("76110573-9d99-4a22-89db-ece1ef672f19");
        facultyList10.add("bedcd231-0561-41f9-802f-a0a6e1da8276");
        facultyList10.add("868072cb-ec2c-4ea6-a287-7c9cccfb0e4d");
        facultyList10.add("a82df7f9-da8c-4a9e-8e2c-8ca56159557f");

        university10.setFacultyList(facultyList10);
        universityService.create(university10);

        // 11. Columbia University
        UniversityCreateDTO university11 = new UniversityCreateDTO();
        university11.setName("Columbia University");
        university11.setWebSite("www.columbia.edu");
        university11.setDescription("<div id=\"rmjs-1\" aria-expanded=\"true\" data-readmore=\"\" data-js-read-more=\"height-650\">\n" +
                "<p>Founded in 1754 as King&rsquo;s College by Royal Charter of King George II of England, Columbia is the oldest university in the state of New York and one of the oldest in the US.</p>\n" +
                "<p>Its main landmark is the Low Memorial Library, which was built in the Roman Classical style and still houses the university&rsquo;s central administration offices.</p>\n" +
                "<p>As well as its main campus in the heart of New York City on Broadway, Columbia has two facilities outside Manhattan: Nevis Laboratories, a centre for the study of high-energy experimental particle and nuclear physics in Irvington, New York, and the Lamont-Doherty Earth Observatory in Palisades, New York.</p>\n" +
                "<p>More than 80 faculty members, adjunct staff and alumni of Columbia have won a Nobel prize since 1901, when the awards were first granted. These include chemist Robert Lefkowitz, economist Joseph Stiglitz and US President Barack Obama, who was given the Peace Prize in 2009.</p>\n" +
                "<p>Columbia has also educated Founding Father of the US Alexander Hamilton, US presidents Theodore and Franklin Roosevelt and actors Jake Gyllenhaal, Katie Holmes and Joseph Gordon-Levitt.</p>\n" +
                "<p>The private research-based university has 20 schools &ndash; which include architecture, planning and preservation; business; Jewish theological seminary; and law &ndash; and 23 libraries that are scattered across the city. Sponsored research from its medical centre produces more than $600 million annually.</p>\n" +
                "<p>Columbia Technology Ventures, the institution&rsquo;s technology transfer office, manages more than 400 new inventions each year and has been involved in launching over 150 start-up companies based on Columbia&rsquo;s technologies.</p>\n" +
                "<p>The university also has nine Columbia Global Centres, which aim to promote and facilitate collaboration between the university&rsquo;s staff, students and alumni in order to address global challenges. These are in China, Jordan, Turkey, Kenya, India, France, Chile, Brazil and New York City.</p>\n" +
                "<p>In 2014-15, the university&rsquo;s total endowment value passed the $9.6 billion mark.</p>\n" +
                "<p>Lee Bollinger became Columbia University&rsquo;s 19th president in 2002, making him the longest-serving leader of an Ivy League institution.</p>\n" +
                "</div>");

        university11.setRating(11L);
        university11.setCountryId(2L);
        university11.setPhotoId("d5618aeb-048b-456c-9db0-19ba8bbf6715");
        university11.setLogoId("d5618aeb-048b-456c-9db0-19ba8bbf6715_logo");
        university11.setDegreeList(degreeTypeList);
        List<String> facultyList11 = new LinkedList<>();
        //Humanities
        facultyList11.add("0fbfc47b-48d2-4337-a60d-32776fbf7fd5");
        facultyList11.add("385d253d-f949-434e-bef5-1d8db8ac2f4f");
        facultyList11.add("8aa164bd-a557-48ac-a26b-8abb0e66eadb");
        facultyList11.add("a1437996-4a98-4e9e-a190-16cfab1a1723");
        facultyList11.add("0532a184-f39c-4823-8b4c-2dad2e1a9053");
        facultyList11.add("6d9afd2c-87be-44a1-9c94-0d32108c3e2c");
        facultyList11.add("16818f9b-254b-4d0d-9561-928aaa0f7d06");
        facultyList11.add("4737df7c-a108-4459-9623-7d0026d5f40b");
        facultyList11.add("ce4f80e9-4613-46a9-aec9-7f565c07baec");
        facultyList11.add("936cdac3-5c8c-4ab9-bc15-a64608540d07");
        facultyList11.add("d5c3be71-4faa-4073-b259-c65d7daf3ffc");
        facultyList11.add("602b762f-6d10-4f02-8e9d-6ab854e22651");
        facultyList11.add("7dd914b3-69b6-41d2-a7fb-9a01e7c9d231");
        facultyList11.add("25867f21-aa5a-47fc-afd6-e51d1af3a06a");
        facultyList11.add("e262b667-d5e2-4393-9d71-54dd3e30f5bb");
        facultyList11.add("38ecdc7a-4920-4e9e-a19c-27cb3ef118fe");
        facultyList11.add("6aadc300-6a4b-4d14-bcfe-bdd035b3b488");
        facultyList11.add("e6d34ff5-b0da-45bd-9b9f-ffcc8ef17882");
        facultyList11.add("e2cef3b9-723b-4ca7-82a1-d7ddf53a7d68");
        facultyList11.add("aa610645-0ad3-41dc-98fc-fa999801a44f");
        facultyList11.add("502c3b7f-f995-46a5-966e-a063601a6657");
        facultyList11.add("c46794c3-b107-4350-88f2-3cc41f4c41fe");
        facultyList11.add("7115d554-74bb-4b48-9696-9f1c2031363d");
        facultyList11.add("11586f1b-13f2-4e8f-93e2-b9d69c922cbf");
        facultyList11.add("dca8e373-56b5-476f-b2f8-d04674fbcff2");
        facultyList11.add("e8596004-45ca-415b-b3fc-555fd719e3ab");
        facultyList11.add("57f9abb7-372d-4e99-986e-3300a5d0f014");
        facultyList11.add("76831345-99db-48a9-b2ee-b6f6790c2456");
        facultyList11.add("a87d40e9-1eea-4d0f-b187-a2e03781fb99");
        //Management
        facultyList11.add("87d064d0-d2a8-4461-8d5c-fc13eb28da84");
        facultyList11.add("89eee0ad-a3e9-4210-b951-e6c6f5df9309");
        facultyList11.add("d6f3270a-6197-4f2a-9efe-52acdfcec923");
        facultyList11.add("9282c1e4-028b-445c-bcc8-cf4c9ebb1975");
        facultyList11.add("6db7b5b2-565f-40ad-a26d-17afd432b3cd");
        facultyList11.add("6b7290de-c9e7-4615-ad85-8e02b1ab3eea");
        facultyList11.add("ec469b1d-d831-4bc1-8072-a14d2a7a27a8");
        facultyList11.add("19bfd42c-e4d5-40cd-8db9-5089259f9374");
        facultyList11.add("aafab318-60fd-404b-95de-338c795e7a58");
        facultyList11.add("87611834-d5c5-4ebc-8e82-c986c76de0f9");
        facultyList11.add("301023c2-11f2-4d44-aea6-4788c741ccf4");
        facultyList11.add("ef0e8afa-f2f5-4c69-b6ac-7e5cedc43465");
        facultyList11.add("436b56bb-e235-47d1-968f-3ac27174868e");
        facultyList11.add("8809e494-705e-4277-8881-ca0342378253");
        facultyList11.add("f48e6a74-c35e-4650-b3c8-4f47e58dabb4");
        facultyList11.add("4441b033-93c2-4fb1-a03e-b6232039fc3a");
        facultyList11.add("b21fb95d-7d5e-449f-a375-aa505226aa70");
        facultyList11.add("96148732-38d5-4cd1-a481-792538f0ead1");
        facultyList11.add("6a139c31-6536-4438-8578-de0ad2470632");
        facultyList11.add("1027a19d-cab7-4542-852b-ecc249f29611");
        facultyList11.add("6665930e-3a67-4710-970f-11e37a684170");
        facultyList11.add("7b3d2ad4-5361-462d-9da2-fab299c1e7bd");
        facultyList11.add("a73931e1-9732-47fd-ad69-a29fcfc43100");
        university11.setFacultyList(facultyList11);
        universityService.create(university11);


        //12. The University of Chicago
        UniversityCreateDTO university12 = new UniversityCreateDTO();
        university12.setName("The University of Chicago");
        university12.setWebSite("www.uchicago.edu");
        university12.setDescription("<div id=\"rmjs-1\" aria-expanded=\"true\" data-readmore=\"\" data-js-read-more=\"height-650\">\n" +
                "<p>The University of Chicago came to life in the twilight of the 19th century, with the state of Illinois issuing its official certificate of incorporation in September 1890. A $600,000 pledge from oil magnate John D. Rockefeller helped to get the university off the ground, while local department store owner Marshall Field donated land. The vision of the university&rsquo;s first president was of a &ldquo;bran splinter new&rdquo; institution &ldquo;as solid as the ancient hills&rdquo; &ndash; a modern research university with a commitment to equal opportunities and non-sectarianism.</p>\n" +
                "<p>This vision has been at the core of Chicago&rsquo;s existence, enshrined in its motto: Crescat scientia; vita excolatur (&ldquo;Let knowledge grow from more to more; and so be human life enriched&rdquo;). The university has lived up to this by being at the forefront of major academic endeavour and discovery. It has connections to more than 80 Nobel laureates, 30 National Medal winners (across humanities, arts and science) and nine Fields Medallists. It has also been awarded nearly 50 MacArthur &ldquo;genius grants&rdquo;.</p>\n" +
                "<p>Current faculty who have won a Nobel prize while at Chicago include economists Robert E. Lucas (1995), James J. Heckman (2000), Roger Myerson (2007), Lars Peter Hansen (2013), Eugene Fama (2013), and physicist James Cronin (1980). Ng&ocirc; Bao Ch&acirc;u, the first Vietnamese to win the Fields Medal (2010), is the Francis and Rose Yuen distinguished service professor in Chicago&rsquo;s department of&nbsp;mathematics.</p>\n" +
                "<p>Notable alumni of Chicago include authors Saul Bellow and Susan Sontag, astronomer Edwin Hubble, film critic Roger Ebert, and everyone&rsquo;s favourite celluloid academic and archaeologist, Indiana Jones &ndash; who also taught at the university.</p>\n" +
                "<p>While Chicago routinely ranks in the world&rsquo;s top institutions academically, its prowess extends to the sporting arena. It was a founding member of the Big Ten Conference, the oldest, highest-level intercollegiate athletics conference in the US. Today the university sponsors 19 intercollegiate sports, which cater for more than 500 participants and 330 competitions taking place each year. They all play under the same name, &ldquo;the Maroons&rdquo;, which the university was nicknamed on account of its official colour.</p>\n" +
                "<p>The university&rsquo;s campus sprawls over more than 210 acres in the Hyde Park and Woodlawn neighbourhoods, which lie south of downtown Chicago. Its first buildings were modelled on the Gothic style of the University of Oxford, but by the mid-20th century, modern buildings had begun popping up to intersperse old and new. It now blends a mix of classical and contemporary architecture from the Mitchell Tower and Robie House, architect Frank Lloyd Wright&rsquo;s historic residence, through to the Laird Bell Law Quadrangle.</p>\n" +
                "<p>The city of Chicago itself is the university&rsquo;s &ldquo;laboratory, playground, and muse&rdquo;, with downtown highlights encompassing restaurants, shopping and cultural attractions. In one day you could visit the Navy Pier amusement park, the Art Institute of Chicago or shop on the Magnificent Mile.</p>\n" +
                "<p>With satellite campuses and facilities overseas, UChicago has transcended its US geography to make itself an international institution. It invites prospective students to step inside its walls and &ldquo;walk along the paths of Nobel laureates, pathbreaking researchers, and tomorrow&rsquo;s leaders&rdquo;.</p>\n" +
                "</div>");

        university12.setRating(12L);
        university12.setCountryId(2L);
        university12.setPhotoId("8d6b1d82-7c0c-4db9-9e35-7297c98e961c");
        university12.setLogoId("8d6b1d82-7c0c-4db9-9e35-7297c98e961c_logo");
        university12.setDegreeList(degreeTypeList);
        List<String> facultyList12 = new LinkedList<>();
        //Natural science faculties
        facultyList12.add("a42c3401-d82f-464c-8054-ad0449b92e8a");
        facultyList12.add("e146c49f-2633-4ca8-87b1-4eccf7b5f9f4");
        facultyList12.add("5ec62766-9129-4432-a881-dbe2be4a0b12");
        facultyList12.add("f17ad858-0935-4ccb-bd1d-ddb3a9d6950c");
        facultyList12.add("4db2f039-c576-4fa2-a08a-ebb5ddd62307");
        facultyList12.add("0cfa779a-97f4-445a-b9b8-44ec21f63e08");
        facultyList12.add("bb737b92-9a67-4a4b-a6a0-883489780ae2");
        facultyList12.add("f5f65c48-4587-4908-b790-481273fc231e");
        facultyList12.add("c993863e-946c-422c-a3c2-7e2af1414d23");
        facultyList12.add("62a4265e-e13f-4690-8fd7-ce3b9b545e20");
        facultyList12.add("ccb0734d-bacd-4b03-8f90-2b810101fa56");
        facultyList12.add("b2a94998-57b8-42bd-85c5-04573ffeeae4");
        facultyList12.add("08faf75a-9e76-406c-80ac-71c895129696");
        facultyList12.add("c8465d31-db8b-46f4-9a42-f1af9f4250f7");
        facultyList12.add("a6ac494b-45cf-4b79-bd81-8cdac14b71db");
        facultyList12.add("fdfe87ee-d240-49c2-9970-6c88b5c74a35");
        facultyList12.add("97f9110f-254e-4394-b42c-259c45aa0f5e");
        facultyList12.add("66f7908a-f801-4a60-bd43-6e3041aa1f0d");
        facultyList12.add("1fa4c796-85e1-4495-bfe0-dbffe04fea97");
        facultyList12.add("b7813475-4128-4e8d-940e-af9a706810c5");
        facultyList12.add("0ee51720-6b79-4651-bd04-87a7d990d966");
        facultyList12.add("93c7ab56-730f-40fd-adcf-4ac384ec3750");
        facultyList12.add("34f285b8-b23b-420c-9994-eaaf4c54ce9d");
        facultyList12.add("df275283-534b-403f-a702-8e6c84459d96");
        facultyList12.add("d07a2f29-1752-4a37-ba48-206bcc6b0d60");
        facultyList12.add("b38804ae-2977-412f-9e1b-6c91be968afa");
        facultyList12.add("5372847f-5b27-41a1-9f10-baa024d2e51b");
        facultyList12.add("30e3813f-6780-477a-b720-218956e3eb90");
        facultyList12.add("fa41bbf7-05f5-45d2-894b-00b9b7f1eb8e");
        facultyList12.add("daa31b19-8078-4f25-bac1-e580c372d65d");
        //Technology faculties
        facultyList12.add("e70f0839-e171-4909-9acb-9a797b8aef85");
        facultyList12.add("4204782a-f2b0-4077-a8bb-944b18f28e87");
        facultyList12.add("3559cc0d-494d-407f-aba6-3f52de4d9d9a");
        facultyList12.add("0611290f-bb76-4ff3-a469-81cf6fd64fac");
        facultyList12.add("94e25c0d-b16f-4a9a-9cda-02b5125fca12");
        facultyList12.add("3aa5e673-bea0-497d-b575-f3def6f9dddf");
        facultyList12.add("8d10af46-6e7a-4083-9f4a-6bd2c4f2b25b");
        facultyList12.add("5b52dce5-eeda-4c67-8db9-c92815a4f81e");
        facultyList12.add("e7e961f0-5281-4525-9a91-79ebf28eeae8");
        facultyList12.add("f579fbd9-1c6c-4d62-b664-6f0c96df6117");
        facultyList12.add("7d0412d5-d93a-421a-8c14-23a034ba3feb");
        facultyList12.add("8d8ba26a-92d6-4a35-8947-8a3559346339");
        facultyList12.add("847bf777-6f1d-44b8-b60f-b6c7b94d3a1c");
        facultyList12.add("9dfdb35b-0bf5-4df1-b60e-8e5d91a1a97c");
        facultyList12.add("cae143a4-7a39-41a3-8db3-c3a128bbc359");
        facultyList12.add("adf7698b-cd15-4538-9f7a-e95336c6a4b1");
        facultyList12.add("1ea51528-d714-4e8b-ac47-11d0ae487185");
        facultyList12.add("cea2ea0d-32eb-4feb-aa5f-88450c6d3603");
        facultyList12.add("2c24fdef-ee88-4e38-ae8c-f3abb65aaa39");
        facultyList12.add("875174d3-2a3d-41ad-b411-18621ce046fc");
        facultyList12.add("e86ff9f9-86a0-4ae7-9c54-70711c1f355b");
        facultyList12.add("991d38a4-d0b6-4dad-b4ff-2c304336bb58");
        facultyList12.add("54933852-6788-4934-9488-5894a2cc0b54");
        facultyList12.add("47090b7e-eda9-4e10-9d58-ab04bd5fa0aa");
        facultyList12.add("66defbf0-a763-4d84-84b5-f759b04d9b36");
        facultyList12.add("a044be33-a551-4ba3-ab7a-5f196fe55984");
        facultyList12.add("600e2beb-f0b4-42e5-b89a-60ddaedd0135");
        facultyList12.add("8c8660b0-b92d-45f7-81c2-09751c51bb50");
        facultyList12.add("227a6ddd-b221-4250-a4f6-a5fee58b085f");
        facultyList12.add("3225bfbc-eaf7-4b00-b8f8-475a1ecb30ca");
        facultyList12.add("9d868d71-4784-4668-947c-bccee9539b95");
        facultyList12.add("f830b1b0-d45f-44b4-849a-ea72c2b560fc");
        university12.setFacultyList(facultyList12);
        universityService.create(university12);


        //13. The University of Chicago
               UniversityCreateDTO university13 = new UniversityCreateDTO();
        university13.setName("Tsinghua University");
        university13.setWebSite("www.tsinghua.edu.cn/en/");
        university13.setDescription("<p>The campus of Tsinghua University is situated on the site of the former imperial gardens of the Qing Dynasty, and surrounded by a number of historical sites in northwest Beijing.</p>\n" +
                "<p>Established in 1911, Tsinghua University is a unique comprehensive university bridging China and the world, " +
                "connecting ancient and modern, and encompassing the arts and sciences. As one of China's most prestigious and influential universities, " +
                "Tsinghua is committed to cultivating global citizens who will thrive in today's world and become tomorrow's leaders. Through the pursuit of education and research at the highest level of excellence, " +
                "Tsinghua is developing innovative solutions that will help solve pressing problems in China and the world.</p>");
        university13.setRating(13L);
        university13.setCountryId(5L);
        university13.setPhotoId("d6552502-3f01-454c-954a-f95b0e2bc67a");
        university13.setLogoId("d6552502-3f01-454c-954a-f95b0e2bc67a_logo");
        university13.setDegreeList(degreeTypeList);
        List<String> facultyList13 = new LinkedList<>();
        //health faculty
        facultyList13.add("8dd31a73-1ab2-403d-b889-e22d2a99fbec");
        facultyList13.add("1400f76a-cdf5-4c4f-a117-d7ba75a600f2");
        facultyList13.add("4f80da53-5f22-4fcb-a0d1-64094dff581a");
        facultyList13.add("b2f0bd87-95c6-402b-884b-55d993a88945");
        facultyList13.add("d52a9e76-03cb-42b4-8a88-409e3c1c94f6");
        facultyList13.add("e76b4c80-0d9a-4e62-aaa7-772a2bd502c8");
        facultyList13.add("40901d76-3aba-44f6-9d8b-e1189bbc67f9");
        facultyList13.add("b529957b-c722-47b2-97c6-b42e5f98e286");
        facultyList13.add("da295505-a4d2-45ef-8304-bc1d2aa138e5");
        facultyList13.add("d622a717-e898-448a-a290-2e5f2ff7fc52");
        facultyList13.add("bddadc1c-24d6-4bbd-8d44-ec913c4462dc");
        facultyList13.add("d6d673d0-5e2c-45fb-b81b-6a94e1e6d4cb");
        facultyList13.add("234b744c-26d8-4094-87e5-7cf6e03cedb1");
        facultyList13.add("f3adddf0-b444-4941-8165-335226565445");
        facultyList13.add("b8b97142-9bb0-4eb6-a47a-b9cb8fcd689a");
        facultyList13.add("e22b3909-ce40-47cf-8b54-b0a706a2df21");
        facultyList13.add("9d9355aa-1730-4474-8389-3eccfcdd1fd9");
        facultyList13.add("8e041211-bcda-458c-90fb-96a0c1d4ccb1");
        facultyList13.add("868648b7-3f49-42ae-b63e-9f2513c2ff85");
        facultyList13.add("835157b4-1670-4122-88ec-488031bc2a5d");
        facultyList13.add("e2552301-56f6-4b17-aaed-dfa05a8e0966");
        facultyList13.add("8ef85093-7c7b-4996-9c8a-c0552a604706");
        facultyList13.add("e10efe65-ab28-440c-9795-b1000de57fab");
        facultyList13.add("1e23ed42-8116-468b-8f77-f4492b87f5f8");
        facultyList13.add("01d4d64f-a0a1-41c2-aad2-6cfb065dc36b");
        facultyList13.add("34e732be-d867-4ed7-b3b2-dd8b57ab7f9f");
        facultyList13.add("db5a9d55-e705-4f9b-95e7-d4a94205a678");
        facultyList13.add("102d5d17-ae9e-4139-ac3b-53476d5d096e");
        facultyList13.add("2b8c5319-3e99-4732-9023-787491b8bb93");
        facultyList13.add("e4ff8e2f-521b-4d6d-889c-a4e69cdf6967");
        facultyList13.add("342309d9-464a-4446-a33b-74bd4e21aaa4");
        facultyList13.add("6c19e270-89e1-492f-9144-5d6a2493ceb3");
        //Natural science faculties
        facultyList13.add("a42c3401-d82f-464c-8054-ad0449b92e8a");
        facultyList13.add("e146c49f-2633-4ca8-87b1-4eccf7b5f9f4");
        facultyList13.add("5ec62766-9129-4432-a881-dbe2be4a0b12");
        facultyList13.add("f17ad858-0935-4ccb-bd1d-ddb3a9d6950c");
        facultyList13.add("4db2f039-c576-4fa2-a08a-ebb5ddd62307");
        facultyList13.add("0cfa779a-97f4-445a-b9b8-44ec21f63e08");
        facultyList13.add("bb737b92-9a67-4a4b-a6a0-883489780ae2");
        facultyList13.add("f5f65c48-4587-4908-b790-481273fc231e");
        facultyList13.add("c993863e-946c-422c-a3c2-7e2af1414d23");
        facultyList13.add("62a4265e-e13f-4690-8fd7-ce3b9b545e20");
        facultyList13.add("ccb0734d-bacd-4b03-8f90-2b810101fa56");
        facultyList13.add("b2a94998-57b8-42bd-85c5-04573ffeeae4");
        facultyList13.add("08faf75a-9e76-406c-80ac-71c895129696");
        facultyList13.add("c8465d31-db8b-46f4-9a42-f1af9f4250f7");
        facultyList13.add("a6ac494b-45cf-4b79-bd81-8cdac14b71db");
        facultyList13.add("fdfe87ee-d240-49c2-9970-6c88b5c74a35");
        facultyList13.add("97f9110f-254e-4394-b42c-259c45aa0f5e");
        facultyList13.add("66f7908a-f801-4a60-bd43-6e3041aa1f0d");
        facultyList13.add("1fa4c796-85e1-4495-bfe0-dbffe04fea97");
        facultyList13.add("b7813475-4128-4e8d-940e-af9a706810c5");
        facultyList13.add("0ee51720-6b79-4651-bd04-87a7d990d966");
        facultyList13.add("93c7ab56-730f-40fd-adcf-4ac384ec3750");
        facultyList13.add("34f285b8-b23b-420c-9994-eaaf4c54ce9d");
        facultyList13.add("df275283-534b-403f-a702-8e6c84459d96");
        facultyList13.add("d07a2f29-1752-4a37-ba48-206bcc6b0d60");
        facultyList13.add("b38804ae-2977-412f-9e1b-6c91be968afa");
        facultyList13.add("5372847f-5b27-41a1-9f10-baa024d2e51b");
        facultyList13.add("30e3813f-6780-477a-b720-218956e3eb90");
        facultyList13.add("fa41bbf7-05f5-45d2-894b-00b9b7f1eb8e");
        facultyList13.add("daa31b19-8078-4f25-bac1-e580c372d65d");
        university13.setFacultyList(facultyList13);
        universityService.create(university13);


        // 14. Peking University
        UniversityCreateDTO university14 = new UniversityCreateDTO();
        university14.setName("Peking University");
        university14.setWebSite("english.pku.edu.cn");
        university14.setDescription("<p>Peking University is a member of the C9 League, analogous to the Ivy League in the United States, and often ranked among the top higher education institutions in China. Only those who score highest in nationwide examinations are admitted, with a total enrolment of about 35,000.</p>\n" +
                "<p>In 1898 the then Imperial University of Peking was founded as the first national university covering comprehensive disciplines in China, replacing the ancient imperial academy which had for hundreds of years trained administrators for China&rsquo;s civil service.</p>\n" +
                "<p>When China became a republic in 1912 the university took on its current name. Its 274 hectare campus sits in Beijing&rsquo;s Haidian District, near the famous Yuanmingyuan Garden and Summer Palace, and is known as known as &lsquo;Yan Yuan&rsquo; or &lsquo;the garden of Yan.&rsquo;</p>\n" +
                "<p>In 1918, Mao Zedong took up a minor post at the university, where he was first exposed to Marxist reading and became a communist. Noted writer Lu Xun also lectured on Chinese literature there.</p>\n" +
                "<p>Later, during the Second Sino-Japanese War, the university had to move to Changsha, and later to Kunming.</p>\n" +
                "<p>The university took on more of a public, rather than national character after the communist revolution, though later had to shut down for four years during the rending change of the cultural revolution.</p>\n" +
                "<p>It played a key part in national protests when in 1989 three thousand of its students, along with students from neighbouring Tsinghua University, erected shrines to Hu Yaobang and later gathered in a mass demonstration at Tiananmen Square.</p>\n" +
                "<p>The university has been crucial to China's modernization, and maintains that it has retained a traditional emphasis on patriotism, progress and science.</p>\n" +
                "<p>Among its faculty, 53 are members of the prestigious Chinese Academy of Sciences, and seven of the equivalent academy of engineering. Peking merged with Beijing Medical University in 2000, adding a range of new medical courses and disciplines.</p>");
        university14.setRating(14L);
        university14.setCountryId(5L);
        university14.setPhotoId("190161e9-e675-4bd4-9dfd-cffd60621f81");
        university14.setLogoId("190161e9-e675-4bd4-9dfd-cffd60621f81_logo");
        university14.setDegreeList(degreeTypeList);
        List<String> facultyList14 = new LinkedList<>();
        //Performing Arts faculties
        facultyList14.add("da5acc41-7669-4c31-bbaf-da604b0560aa");
        facultyList14.add("ff65d6a1-6b58-432a-9136-e86a469b7fc2");
        facultyList14.add("e144fcd6-60b9-4ad8-9d90-bb762708d081");
        facultyList14.add("c258b615-d5a8-456a-8a10-495a0e30ac61");
        facultyList14.add("d63f6f9d-aed4-437d-b90f-e866e73aff56");
        facultyList14.add("5cd6a2ef-ee86-4713-8d3f-4226b908e031");
        facultyList14.add("2e25904f-c66d-475f-9dd1-f5c90d1bf329");
        facultyList14.add("b2d764cb-2ad1-401b-a3aa-6a00792522db");
        facultyList14.add("1e039eb7-91b7-4096-85b8-45a854011a50");
        facultyList14.add("1b0ef979-de5a-4a26-86f6-55dd31a78105");
        facultyList14.add("fb233a10-82b3-481a-b281-20cff1bd9df8");
        facultyList14.add("e66601d8-0dff-4ad3-9780-db4aad50a489");
        facultyList14.add("3ae65b82-b7b5-40bb-87ef-66c53834f2b8");
        facultyList14.add("dbc9747d-ccef-49e0-a9de-c0aa9ee98067");
        facultyList14.add("f6e93948-e79a-4e2f-bde7-08c3b72deb59");
        facultyList14.add("19760f97-ed4c-4c92-831a-3b738747c5ab");
        facultyList14.add("90f87e86-4f51-44e8-a987-0662c583ed58");
        facultyList14.add("83c65208-7f2f-460f-b5c8-f01fd8624eb3");
        facultyList14.add("21474cc2-7505-49f8-b7fb-c48de03cf1c2");
        facultyList14.add("92e939b3-0b56-430c-9f03-4908da8fbb10");
        facultyList14.add("060738ee-aa71-48ce-8499-99d52c2c0522");
        //Languages faculties
        facultyList14.add("8d7f739d-cc08-41a4-b838-db94b1ac679b");
        facultyList14.add("dd7a65d7-0c69-4bb6-a114-67e372ec030f");
        facultyList14.add("b4aa1e41-c2f0-4d6c-870b-f7d653763a52");
        facultyList14.add("777163b7-5562-4e83-a902-bac62806a37e");
        facultyList14.add("029bbf6f-2c88-4005-8bf6-e3f5d8997cd8");
        facultyList14.add("14bed110-886f-45d5-9a20-21682eb79df2");
        facultyList14.add("6b1e61d5-3e68-4d3c-937b-af8c8e6735c2");
        facultyList14.add("f34cbf7c-f6ff-4458-b82f-9d96b1da3acd");
        facultyList14.add("079e7917-fcdc-43ff-9068-d829654e9a0c");
        facultyList14.add("11b78ffb-eb50-4c9e-bf8a-c6550aec85a3");
        facultyList14.add("6cf5037c-4543-4a60-aea1-ee1bdda3c0c5");
        facultyList14.add("4edf27a0-8140-4c4c-981d-569c575cbbf9");
        facultyList14.add("9923759e-e943-46ee-b053-9f009f8098b9");
        facultyList14.add("dc10c10c-84db-4e88-8f04-ea38ff904362");
        facultyList14.add("4acdba7f-61f1-4bef-9b58-3714d3ad10be");
        facultyList14.add("912edfb4-1173-44a4-9721-0e4b124a0bcb");
        facultyList14.add("ba2999d0-7c95-446a-b7a3-2037e862d390");
        facultyList14.add("428d5bf1-d724-4894-a07a-bba22bddbb58");
        facultyList14.add("463e52b3-1342-4c8f-8983-6724b08ee0ab");
        facultyList14.add("04a46a8d-cd88-486d-80cb-47393ef0f431");
        university14.setFacultyList(facultyList14);
        universityService.create(university14);


        //15 University of Toronto
        UniversityCreateDTO university15 = new UniversityCreateDTO();
        university15.setName("University of Toronto");
        university15.setWebSite("www.utm.utoronto.ca");
        university15.setDescription("<p>The University of Toronto (UofT) is among the world&rsquo;s most prestigious universities. Founded in 1827, it offers over 700 undergraduate degree and 200 postgraduate degree programmes to a cohort of almost 60,000 students.</p>\n" +
                "<p>With a longstanding reputation for innovation and research, the University of Toronto was the birthplace of such ground-breaking scientific moments as the discovery of insulin and stem cell research, and the invention of the electron microscope.</p>\n" +
                "<p>The university also cites teaching as a strength in disciplines spanning medicine, business, engineering, humanities, education, and more.</p>\n" +
                "<p>With a reputation for producing leaders, it counts five Canadian prime ministers among its former students and associations with 10 Nobel laureates. Other notable alumni include the actor Donald Sutherland and the writers Margaret Atwood and Michael Ondaatje.</p>\n" +
                "<p>It is a multi-campus university, with its St George campus in downtown Toronto likened to Oxford and Cambridge on account of its mix of green space and historical architecture.</p>\n" +
                "<p>The university&rsquo;s Mississauga campus, on the banks of the Credit River&nbsp;to the west of the city, includes 225 acres of protected green belt, while its Scarborough campus is home to a new aquatics centre.</p>\n" +
                "<p>The university attracts many sports fans and boasts a fearsome reputation in both football and ice-hockey. Other campus attractions include more than 1,000 student organisations in addition to a recreational centre and student facilities that include an art gallery, theatre and concert hall.</p>\n" +
                "<p>Toronto itself is one of the most dynamic, vibrant cities in the world, one which places a high&nbsp;priority on arts and culture, and diversity and tolerance.</p>\n" +
                "<p>The city is also major international centre for business and finance, and is the third largest hub for film and television production after Los Angeles and New York.</p>\n" +
                "<p>With international students enrolled from around 160 countries, the university prides itself on being as cosmopolitan and multicultural as the city it inhabits.</p>\n" +
                "<p>Laying claim to a global mindset, the university actively partners with other leading higher education institutions and industry partners around the world to provide international opportunities to its students and faculty.</p>");
        university15.setRating(15L);
        university15.setCountryId(9L);
        university15.setPhotoId("886be858-3a54-411b-b082-f4919018b4e5");
        university15.setLogoId("886be858-3a54-411b-b082-f4919018b4e5_logo");
        university15.setDegreeList(degreeTypeList);
        List<String> facultyList15 = new LinkedList<>();
        //Technology faculties
        facultyList15.add("e70f0839-e171-4909-9acb-9a797b8aef85");
        facultyList15.add("4204782a-f2b0-4077-a8bb-944b18f28e87");
        facultyList15.add("3559cc0d-494d-407f-aba6-3f52de4d9d9a");
        facultyList15.add("0611290f-bb76-4ff3-a469-81cf6fd64fac");
        facultyList15.add("94e25c0d-b16f-4a9a-9cda-02b5125fca12");
        facultyList15.add("3aa5e673-bea0-497d-b575-f3def6f9dddf");
        facultyList15.add("8d10af46-6e7a-4083-9f4a-6bd2c4f2b25b");
        facultyList15.add("5b52dce5-eeda-4c67-8db9-c92815a4f81e");
        facultyList15.add("e7e961f0-5281-4525-9a91-79ebf28eeae8");
        facultyList15.add("f579fbd9-1c6c-4d62-b664-6f0c96df6117");
        facultyList15.add("7d0412d5-d93a-421a-8c14-23a034ba3feb");
        facultyList15.add("8d8ba26a-92d6-4a35-8947-8a3559346339");
        facultyList15.add("847bf777-6f1d-44b8-b60f-b6c7b94d3a1c");
        facultyList15.add("9dfdb35b-0bf5-4df1-b60e-8e5d91a1a97c");
        facultyList15.add("cae143a4-7a39-41a3-8db3-c3a128bbc359");
        facultyList15.add("adf7698b-cd15-4538-9f7a-e95336c6a4b1");
        facultyList15.add("1ea51528-d714-4e8b-ac47-11d0ae487185");
        facultyList15.add("cea2ea0d-32eb-4feb-aa5f-88450c6d3603");
        facultyList15.add("2c24fdef-ee88-4e38-ae8c-f3abb65aaa39");
        facultyList15.add("875174d3-2a3d-41ad-b411-18621ce046fc");
        facultyList15.add("e86ff9f9-86a0-4ae7-9c54-70711c1f355b");
        facultyList15.add("991d38a4-d0b6-4dad-b4ff-2c304336bb58");
        facultyList15.add("54933852-6788-4934-9488-5894a2cc0b54");
        facultyList15.add("47090b7e-eda9-4e10-9d58-ab04bd5fa0aa");
        facultyList15.add("66defbf0-a763-4d84-84b5-f759b04d9b36");
        facultyList15.add("a044be33-a551-4ba3-ab7a-5f196fe55984");
        facultyList15.add("600e2beb-f0b4-42e5-b89a-60ddaedd0135");
        facultyList15.add("8c8660b0-b92d-45f7-81c2-09751c51bb50");
        facultyList15.add("227a6ddd-b221-4250-a4f6-a5fee58b085f");
        facultyList15.add("3225bfbc-eaf7-4b00-b8f8-475a1ecb30ca");
        facultyList15.add("9d868d71-4784-4668-947c-bccee9539b95");
        facultyList15.add("f830b1b0-d45f-44b4-849a-ea72c2b560fc");
        //General Studies
        facultyList15.add("7bd2efde-13a5-4f2f-b488-2945ea161192");
        facultyList15.add("99ff1500-b874-4685-9aef-56fd5fef8489");
        facultyList15.add("76110573-9d99-4a22-89db-ece1ef672f19");
        facultyList15.add("bedcd231-0561-41f9-802f-a0a6e1da8276");
        facultyList15.add("868072cb-ec2c-4ea6-a287-7c9cccfb0e4d");
        facultyList15.add("a82df7f9-da8c-4a9e-8e2c-8ca56159557f");



        //16 University of Singapore
        UniversityCreateDTO university16 = new UniversityCreateDTO();
        university16.setName("National University of Singapore");
        university16.setWebSite(" www.nus.edu.sg");
        university16.setDescription("<p>The National University of Singapore (NUS) is Singapore&rsquo;s flagship university, which offers a global approach to education, research and entrepreneurship, with a focus on Asian perspectives and expertise. We have 16 colleges, faculties and schools across three campuses in Singapore, with more than 40,000 students from 100 countries enriching our vibrant and diverse campus community. We have also established our NUS Overseas Colleges programme in more than 15 cities around the world.</p>\n" +
                "<p>Our multidisciplinary and real-world approach to education, research and entrepreneurship enables us to work closely with industry, governments and academia to address crucial and complex issues relevant to Asia and the world. Researchers in our faculties, university-level research institutes, research centres of excellence and corporate labs focus on themes that include energy; environmental and urban sustainability; treatment and prevention of diseases; active ageing; advanced materials; risk management and resilience of financial systems; Asian studies; and Smart Nation capabilities such as artificial intelligence, data science, operations research and cybersecurity.</p>\n" +
                "<p>For more information on NUS, please visit&nbsp;<a href=\"http://www.nus.edu.sg/\" data-mz=\"\">www.nus.edu.sg</a>.</p>");
        university16.setRating(16L);
        university16.setCountryId(42L);
        university16.setPhotoId("4dc8253d-5627-44c7-bd3e-b355472c088f");
        university16.setLogoId("4dc8253d-5627-44c7-bd3e-b355472c088f_logo");
        university16.setDegreeList(degreeTypeList);
        List<String> facultyList16 = new LinkedList<>();
        //Law faculties
        facultyList16.add("69a572eb-833e-4277-9ae1-ca30d82ad971");
        facultyList16.add("90114f6c-1e6d-49fd-b265-7c2184597160");
        facultyList16.add("6dffbbf2-5349-4077-b795-f21130b8c47e");
        facultyList16.add("4d0d0859-ad13-47d8-9bb2-328af21f78ab");
        facultyList16.add("2731b43b-ef08-4276-9462-25695743a0dd");
        facultyList16.add("fad36318-5aa5-4954-8f0b-a3143c4c515a");
        facultyList16.add("a206a4b6-395e-485e-af81-b45fa1980b10");
        facultyList16.add("9c29e1d1-01f5-48e5-bbe3-7680d10592eb");
        facultyList16.add("3b74931c-8940-44b7-80ff-27fd7a1cd396");
        facultyList16.add("ad01448c-d444-4df9-96e9-e93fbdf0d9ed");
        facultyList16.add("e94990db-6274-4c49-9281-f1dcc74e38c7");
        facultyList16.add("6b588fd2-e603-4675-acfd-0c9d99c35256");
        facultyList16.add("68db28eb-91c7-403e-8b75-8592020549a4");
        facultyList16.add("b82f23b9-e4bd-4211-8a8d-67464a5d2a65");
        facultyList16.add("3e1bc1bb-411e-4c82-b200-ec6bd92d0213");
        facultyList16.add("37b687c3-0e0c-4d2f-882c-7feb954ea731");
        facultyList16.add("79c7d492-8dff-4360-b14a-67d501d7a37e");
        facultyList16.add("a19167e0-26c4-41fb-b68d-0d88bf211f87");
        facultyList16.add("691a45f2-8e8b-425d-b07e-f262ef5758d0");
        facultyList16.add("b089b888-5d28-4d0f-8bc6-3de806b7bb8b");
        facultyList16.add("9fab6d1c-b9f4-463a-8541-20ef85bac7ad");
        facultyList16.add("9d5ec1a2-9216-4797-b57a-3fb45916a6f1");
        facultyList16.add("ea99de33-08cc-442f-87f2-71076d1221a0");
        facultyList16.add("1a6c8347-df32-4720-9676-1c13031a6716");
        facultyList16.add("c4dc9a44-bf48-44dc-a38e-5da4ad6a1cf5");
        // Managment faculties
        facultyList16.add("87d064d0-d2a8-4461-8d5c-fc13eb28da84");
        facultyList16.add("89eee0ad-a3e9-4210-b951-e6c6f5df9309");
        facultyList16.add("d6f3270a-6197-4f2a-9efe-52acdfcec923");
        facultyList16.add("9282c1e4-028b-445c-bcc8-cf4c9ebb1975");
        facultyList16.add("6db7b5b2-565f-40ad-a26d-17afd432b3cd");
        facultyList16.add("6b7290de-c9e7-4615-ad85-8e02b1ab3eea");
        facultyList16.add("ec469b1d-d831-4bc1-8072-a14d2a7a27a8");
        facultyList16.add("19bfd42c-e4d5-40cd-8db9-5089259f9374");
        facultyList16.add("aafab318-60fd-404b-95de-338c795e7a58");
        facultyList16.add("87611834-d5c5-4ebc-8e82-c986c76de0f9");
        facultyList16.add("301023c2-11f2-4d44-aea6-4788c741ccf4");
        facultyList16.add("ef0e8afa-f2f5-4c69-b6ac-7e5cedc43465");
        facultyList16.add("436b56bb-e235-47d1-968f-3ac27174868e");
        facultyList16.add("8809e494-705e-4277-8881-ca0342378253");
        facultyList16.add("f48e6a74-c35e-4650-b3c8-4f47e58dabb4");
        facultyList16.add("4441b033-93c2-4fb1-a03e-b6232039fc3a");
        facultyList16.add("b21fb95d-7d5e-449f-a375-aa505226aa70");
        facultyList16.add("96148732-38d5-4cd1-a481-792538f0ead1");
        facultyList16.add("6a139c31-6536-4438-8578-de0ad2470632");
        facultyList16.add("1027a19d-cab7-4542-852b-ecc249f29611");
        facultyList16.add("6665930e-3a67-4710-970f-11e37a684170");
        facultyList16.add("7b3d2ad4-5361-462d-9da2-fab299c1e7bd");
        facultyList16.add("a73931e1-9732-47fd-ad69-a29fcfc43100");
        university16.setFacultyList(facultyList16);
        universityService.create(university16);


        //17. University of UCL(london)
        UniversityCreateDTO university17 = new UniversityCreateDTO();
        university17.setName("UCL (LONDON'S GLOBAL UNIVERSITY)");
        university17.setWebSite("www.ucl.ac.uk");
        university17.setDescription("<p>UCL was founded in 1826 to bring higher education to those who were typically excluded from it. In 1878, it became the first university in England to admit women on equal terms as men.</p>\n" +
                "<p>Located in the heart of London, UCL is a constituent college of the University of London and a member of the Russell Group, with approximately 850 professors and over 6,000 academic and research staff.&nbsp;</p>\n" +
                "<p>UCL comprises 11 faculties:&nbsp;Arts and Humanities, Built Environment, Brain Sciences, Engineering, the Institute of Education, Laws, Life Sciences, Mathematical and Physical Sciences, Medical Sciences, Population Health Sciences, and Social and Historical Sciences.</p>\n" +
                "<p>Throughout its history, UCL has been the birthplace of numerous significant scientific discoveries, with 29 Nobel Prizes awarded to UCL students or staff, including William Ramsay, who won the Nobel Prize in Chemistry in 1904 for his discovery of the noble gases.</p>\n" +
                "<p>In addition, the UCL academic community includes 53 Fellows of the Royal Society, 51 Fellows of the British Academy, 15 Fellows of the Royal Academy of Engineering, and 117 Fellows of the Academy of Medical Sciences.</p>\n" +
                "<p>The student body is nearly 36,000-strong and UCL has one of the largest systems of postgraduate study in the country. Nearly 52 per cent of students are engaged in graduate studies.</p>\n" +
                "<p>Students hail from approximately 150 countries worldwide making up more than one-third of the university&rsquo;s entire student population.</p>\n" +
                "<p>UCL was the first British university to open a campus in Doha, Qatar, where it runs a centre for the study of cultural heritage. It also has a presence in Adelaide, South Australia, which includes a space science&nbsp;and an energy policy institute.</p>\n" +
                "<p>UCL alumni include film director Derek Jarman, the writer Lynne Truss, Baroness Patricia Scotland, who became the UK&rsquo;s first female Attorney General, and Marie Stopes, who founded Britain&rsquo;s first family planning clinic.</p>\n" +
                "<p>The university&rsquo;s Latin motto translates as &lsquo;Let all come who by merit deserve the most reward.&rsquo;</p>");
        university17.setRating(17L);
        university17.setCountryId(6L);
        university17.setPhotoId("eaa579e4-a33f-456e-a646-1c7ce896a3cb");
        university17.setLogoId("eaa579e4-a33f-456e-a646-1c7ce896a3cb_logo");
        university17.setDegreeList(degreeTypeList);
        List<String> facultyList17 = new LinkedList<>();
        // Managment faculties
        facultyList17.add("87d064d0-d2a8-4461-8d5c-fc13eb28da84");
        facultyList17.add("89eee0ad-a3e9-4210-b951-e6c6f5df9309");
        facultyList17.add("d6f3270a-6197-4f2a-9efe-52acdfcec923");
        facultyList17.add("9282c1e4-028b-445c-bcc8-cf4c9ebb1975");
        facultyList17.add("6db7b5b2-565f-40ad-a26d-17afd432b3cd");
        facultyList17.add("6b7290de-c9e7-4615-ad85-8e02b1ab3eea");
        facultyList17.add("ec469b1d-d831-4bc1-8072-a14d2a7a27a8");
        facultyList17.add("19bfd42c-e4d5-40cd-8db9-5089259f9374");
        facultyList17.add("aafab318-60fd-404b-95de-338c795e7a58");
        facultyList17.add("87611834-d5c5-4ebc-8e82-c986c76de0f9");
        facultyList17.add("301023c2-11f2-4d44-aea6-4788c741ccf4");
        facultyList17.add("ef0e8afa-f2f5-4c69-b6ac-7e5cedc43465");
        facultyList17.add("436b56bb-e235-47d1-968f-3ac27174868e");
        facultyList17.add("8809e494-705e-4277-8881-ca0342378253");
        facultyList17.add("f48e6a74-c35e-4650-b3c8-4f47e58dabb4");
        facultyList17.add("4441b033-93c2-4fb1-a03e-b6232039fc3a");
        facultyList17.add("b21fb95d-7d5e-449f-a375-aa505226aa70");
        facultyList17.add("96148732-38d5-4cd1-a481-792538f0ead1");
        facultyList17.add("6a139c31-6536-4438-8578-de0ad2470632");
        facultyList17.add("1027a19d-cab7-4542-852b-ecc249f29611");
        facultyList17.add("6665930e-3a67-4710-970f-11e37a684170");
        facultyList17.add("7b3d2ad4-5361-462d-9da2-fab299c1e7bd");
        facultyList17.add("a73931e1-9732-47fd-ad69-a29fcfc43100");
        //business favulties
        facultyList17.add("846e13da-a586-4521-804b-e5d2800b5896");
        facultyList17.add("2a0f19d7-bce5-4cfb-8c75-5012adefa8dd");
        facultyList17.add("4e3f7d0c-de9d-4d34-9d64-8e0662e698a0");
        facultyList17.add("2542382b-deb0-4c3b-9506-4854d4fac916");
        facultyList17.add("bd776090-d7ee-46fb-8435-9e5d6eb673ba");
        facultyList17.add("0869bb07-2252-43f5-8041-51daff6bca41");
        facultyList17.add("dbca9992-5878-4e34-8772-962d19c8deb6");
        facultyList17.add("5fab9d1d-e6cb-4e72-8e4b-93996d17dbde");
        facultyList17.add("e1c6cd2c-48d0-4e1a-9f77-16be04ffefae");
        facultyList17.add("d4538b6e-5935-4f21-9101-7a60139a0561");
        facultyList17.add("2df80be6-871a-4727-8228-370b935b8eca");
        facultyList17.add("cdef7d59-1bb3-4715-b53e-afb9bd2bd81e");
        facultyList17.add("db971772-055f-4272-86de-efdcb3583527");
        facultyList17.add("1eb11b0f-6b34-4973-bda7-b20dd2540073");
        facultyList17.add("e8cba975-0992-4879-b6b8-0367dba2724f");
        facultyList17.add("efec9ff9-a8a1-489d-a0ad-843ddcc8f8dc");
        facultyList17.add("22bf7776-282f-4c57-a861-ebe2354d1099");
        facultyList17.add("d3e8d0a1-8116-46d0-852d-f848a67944d4");
        facultyList17.add("1abd7454-ba9f-4569-ae13-451158030ecf");
        facultyList17.add("825e0804-4d44-4742-ad1e-bde1cf278973");
        facultyList17.add("10c4e7c9-8496-4449-a240-79acbc463078");
        facultyList17.add("2f97a4d9-5812-4840-94f1-609f6ba91f4d");
        facultyList17.add("505fbda5-c592-47bd-8480-27d269516d50");
        facultyList17.add("67c495ee-655d-4393-b5d3-00f316a751c1");
        facultyList17.add("2502f63b-3f0a-40b1-9025-015493f83308");
        facultyList17.add("a548445e-4297-48fc-94ad-2a6f289d227c");
        facultyList17.add("ef0b91da-1fe3-45de-bfe7-282158cd9b9c");
        facultyList17.add("0cdc6486-271b-45e4-b517-988407ad6e0e");
        facultyList17.add("67b73605-bd51-4774-8657-6cf71be426e5");
        facultyList17.add("08284360-7d6f-4b85-a599-2f7ef1424a35");
        facultyList17.add("a0b210cc-a7db-41b2-8842-470683c94406");
        facultyList17.add("6177c87b-b552-4b55-8a52-d416ec37e22b");
        facultyList17.add("fb5c6ce3-ca1c-4028-871a-24d7f918f3be");
        facultyList17.add("eb7dde8a-2c44-4d99-b5d5-c66a3dfc6803");
        facultyList17.add("a3096239-bbd1-4b23-b2e2-ad6029f46c6e");
        facultyList17.add("95fdefd9-65ac-48d9-a0aa-32673a7e1fb4");
        facultyList17.add("f0febd67-e4dc-4055-b7be-7beb7587ff5c");
        facultyList17.add("a67a5a6b-e113-46f0-90d1-5e45ed231ac0");
        facultyList17.add("c94c7d91-b47d-4dfa-b478-c42f72521ea0");
        facultyList17.add("4f1ed038-a928-44b8-bcfd-c6089a8f0157");
        facultyList17.add("c0c17f05-325d-49ff-9661-0759698b9e37");
        facultyList17.add("caa5de66-07ff-404c-85ef-0162069aa4c8");
        facultyList17.add("7933edfc-92dd-49ae-8e23-163bf2707e3c");
        university17.setFacultyList(facultyList17);
        universityService.create(university17);


        //18. Technical University of Munich
        UniversityCreateDTO university18 = new UniversityCreateDTO();
        university18.setName("Technical University of Munich");
        university18.setWebSite("www.tum.de");
        university18.setDescription("<p>Winged by its entrepreneurial spirit, and encouraged by its proven potential for progressive change, the Technical University of Munich (TUM) has become Germany&rsquo;s flagship university of technology, awarded the title &ldquo;University of Excellence&rdquo; three times in row since the birth of Germany&rsquo;s Excellence Initiative. &nbsp;</p>\n" +
                "<p>TUM draws its strength from the research and education excellence of its seven TUM Schools and reinforces transdisciplinary innovations through mission-driven Integrative Research Institutes. In response to rapid societal change in the age of digitalization and biologization, TUM has been fundamentally reforming the concept of engineering, opening it up to natural and life sciences, medicine, business management, humanities and social sciences.</p>\n" +
                "<p>People are at the heart of TUM&lsquo;s aspirations. The central mission aims to enabling diverse talent across all levels, advancing education, new knowledge creation, and entrepreneurial venturing, and shaping future labor markets aligned to society&rsquo;s values and the needs of our natural environment. From a transnational community of curious, open-minded students all the way up to global alumni, TUM leverages the intelligence of the entire TUM family, mobilizes the unique Greater Munich&rsquo;s science and industry network, and partners with leading global institutions.</p>");
        university18.setRating(18L);
        university18.setCountryId(10L);
        university18.setPhotoId("5b49146d-8256-42ae-a31a-01f9324b3718");
        university18.setLogoId("5b49146d-8256-42ae-a31a-01f9324b3718_logo");
        university18.setDegreeList(degreeTypeList);
        List<String> facultyList18 = new LinkedList<>();
        //Technology faculties
        facultyList18.add("e70f0839-e171-4909-9acb-9a797b8aef85");
        facultyList18.add("4204782a-f2b0-4077-a8bb-944b18f28e87");
        facultyList18.add("3559cc0d-494d-407f-aba6-3f52de4d9d9a");
        facultyList18.add("0611290f-bb76-4ff3-a469-81cf6fd64fac");
        facultyList18.add("94e25c0d-b16f-4a9a-9cda-02b5125fca12");
        facultyList18.add("3aa5e673-bea0-497d-b575-f3def6f9dddf");
        facultyList18.add("8d10af46-6e7a-4083-9f4a-6bd2c4f2b25b");
        facultyList18.add("5b52dce5-eeda-4c67-8db9-c92815a4f81e");
        facultyList18.add("e7e961f0-5281-4525-9a91-79ebf28eeae8");
        facultyList18.add("f579fbd9-1c6c-4d62-b664-6f0c96df6117");
        facultyList18.add("7d0412d5-d93a-421a-8c14-23a034ba3feb");
        facultyList18.add("8d8ba26a-92d6-4a35-8947-8a3559346339");
        facultyList18.add("847bf777-6f1d-44b8-b60f-b6c7b94d3a1c");
        facultyList18.add("9dfdb35b-0bf5-4df1-b60e-8e5d91a1a97c");
        facultyList18.add("cae143a4-7a39-41a3-8db3-c3a128bbc359");
        facultyList18.add("adf7698b-cd15-4538-9f7a-e95336c6a4b1");
        facultyList18.add("1ea51528-d714-4e8b-ac47-11d0ae487185");
        facultyList18.add("cea2ea0d-32eb-4feb-aa5f-88450c6d3603");
        facultyList18.add("2c24fdef-ee88-4e38-ae8c-f3abb65aaa39");
        facultyList18.add("875174d3-2a3d-41ad-b411-18621ce046fc");
        facultyList18.add("e86ff9f9-86a0-4ae7-9c54-70711c1f355b");
        facultyList18.add("991d38a4-d0b6-4dad-b4ff-2c304336bb58");
        facultyList18.add("54933852-6788-4934-9488-5894a2cc0b54");
        facultyList18.add("47090b7e-eda9-4e10-9d58-ab04bd5fa0aa");
        facultyList18.add("66defbf0-a763-4d84-84b5-f759b04d9b36");
        facultyList18.add("a044be33-a551-4ba3-ab7a-5f196fe55984");
        facultyList18.add("600e2beb-f0b4-42e5-b89a-60ddaedd0135");
        facultyList18.add("8c8660b0-b92d-45f7-81c2-09751c51bb50");
        facultyList18.add("227a6ddd-b221-4250-a4f6-a5fee58b085f");
        facultyList18.add("3225bfbc-eaf7-4b00-b8f8-475a1ecb30ca");
        facultyList18.add("9d868d71-4784-4668-947c-bccee9539b95");
        facultyList18.add("f830b1b0-d45f-44b4-849a-ea72c2b560fc");
        //Physical Sciences
        facultyList18.add("ebb0674d-60b7-48ea-bab5-03fd6fd7ec9d");
        facultyList18.add("47249de0-384b-4fb1-a55f-b5d800fbd676");
        facultyList18.add("211a1334-1ada-45b3-86be-ef1301bdde33");
        facultyList18.add("cb5ae418-7cf3-4925-927d-ba69bc442cbf");
        facultyList18.add("41afbbe1-aa34-421f-80d3-bc9e0a077361");
        facultyList18.add("e2e6bbd6-37bc-4cfd-a4a1-65935aef7a7b");
        facultyList18.add("3a6019be-9ad0-4b04-ac14-257df64208db");
        facultyList18.add("349ddeec-fc73-4004-a254-ebdb842b8f66");
        facultyList18.add("d35b8488-a70c-4b4f-86af-f889112e6184");
        facultyList18.add("6b606102-87f4-44d9-97b2-ddebcb0861d6");
        facultyList18.add("54275e85-0d96-4623-b68f-8bc38598761c");
        facultyList18.add("88fe4c0f-272e-488e-b20a-cbe4b7859c9d");
        facultyList18.add("641fb142-b650-4d3a-9f01-f9de7f57cea5");
        facultyList18.add("5be74340-c3da-487a-8c16-6aeb9a25c1f7");
        facultyList18.add("8cd55cc4-360c-4530-9e49-a1052b065d43");
        facultyList18.add("067a2a51-b5d2-4d92-ae1f-6568ea49f410");
        facultyList18.add("f5700727-72e7-4804-9f4f-b6ffb7ce98f9");
        facultyList18.add("c36005a7-6dfc-44b0-82fa-c1bf50aa8a24");
        facultyList18.add("ee7c71a3-48ce-42b1-b782-408bb7bd6794");
        facultyList18.add("a5c424f6-762f-4187-9f91-cadc32445611");
        facultyList18.add("23eee949-7f8c-471c-af12-63e8e76d530c");
        facultyList18.add("27d34c29-b132-451f-82ea-fdcc3dd83a2b");
        facultyList18.add("5c9e1167-a38b-4d72-967c-9b1d51c6879e");
        university18.setFacultyList(facultyList18);
        universityService.create(university18);


        //19. University of Hong Kong
        UniversityCreateDTO university19 = new UniversityCreateDTO();
        university19.setName("University of Hong Kong");
        university19.setWebSite("www.hku.hk");
        university19.setDescription("<p>Founded in 1911, the University of Hong Kong is the oldest tertiary-education institution in Hong Kong and has a global reputation for excellence in teaching, research and innovation.</p>\n" +
                "<p>HKU is currently ranked 4th in the Times Higher Education Asia University Rankings, and 31st in the World University Rankings. It is also the world&rsquo;s most international university, according to the THE rankings, an award based on its international student score, its international staff score, its international co-authorship score and the international reputation metrics collected by THE.</p>\n" +
                "<p>HKU is internationally recognised for its accomplishments as a research-led university. Researchers at HKU repeatedly secure the lion&rsquo;s share of competitive government research funding and HKU prides itself on having a wide range of international collaborations. These include research projects, visiting professorships and joint doctorate programmes with world-class institutions. As part of this work, HKU actively participates in international networks, with 38 academics named on the list of &ldquo;Highly Cited Researchers 2022&rdquo; from Clarivate.</p>\n" +
                "<p>HKU has 10 academic faculties where English is the main language of instruction. They include the Faculty of Architecture, Arts, Business and Economics (HKU Business School), Dentistry, Education, Engineering, Law, Medicine (Li Ka Shing Faculty of Medicine), Science and Social Sciences.</p>\n" +
                "<p>As a global university, the HKU campus welcomes more than 5,000 international students from 90 countries and regions. In addition, HKU has established relationships and partnerships with more than 430 leading universities around the world in 47 countries, carrying out academic exchanges and scientific research cooperation. It provides abundant opportunities for students to study abroad.</p>");
        university19.setRating(19L);
        university19.setCountryId(5L);
        university19.setPhotoId("74eb67a5-28e3-4deb-9f62-7869db5d3077");
        university19.setLogoId("74eb67a5-28e3-4deb-9f62-7869db5d3077_logo");
        university19.setDegreeList(degreeTypeList);
        List<String> facultyList19 = new LinkedList<>();
        //Architecture
        facultyList19.add("fb31520b-21c4-4012-b074-2e68d01a0a86");
        facultyList19.add("e9baf081-98bf-44d9-956c-2e53303cfffc");
        facultyList19.add("76949a37-2d3b-4ce6-b4ab-9f67f1b561f3");
        facultyList19.add("72917341-78f1-4c76-a391-a14c63ad2c2e");
        facultyList19.add("02da017b-ecd7-4bb0-b56d-e2959933c374");
        facultyList19.add("5f9af5d7-db40-468f-9c66-e064d1479cc4");
        facultyList19.add("8939c2aa-ba01-4659-85c4-3af00499662e");
        facultyList19.add("b06db058-5db1-4581-a262-44047322677d");
        facultyList19.add("ce027fa4-2ad2-449a-8faa-64e962668493");
        facultyList19.add("1aaa73fc-9227-4c8e-9580-b214a21ae37d");
        facultyList19.add("5f44e8aa-608f-400f-bddf-d24360fb092d");
        facultyList19.add("b37f126a-f9fd-4bb4-8c48-8500483b411a");
        facultyList19.add("3e7f543c-a53f-40b2-a0c8-5c63a78e0d60");
        facultyList19.add("ae45281b-5a87-4dfc-a717-a7341a3e55f7");
        facultyList19.add("35ea02d7-bbbf-4fa9-b2bf-414158706254");
        facultyList19.add("dc2d02fc-ce4c-4102-bb7b-277ba03dc7d2");
        facultyList19.add("f28e53f6-f510-417a-9a46-387a97bd4142");
        facultyList19.add("655829ec-f0bd-43ad-ac6f-e899f4cb275c");
        //Education faculties
        facultyList19.add("9a9e94fc-19e5-4fd3-b2e8-645f669faddb");
        facultyList19.add("b3c69f2d-348e-4486-b965-68b43e46846f");
        facultyList19.add("c0690064-fd33-4f0b-904a-6b1d5f06ac43");
        facultyList19.add("bf6434cb-3be4-469b-bd51-8dbc2c7c3793");
        facultyList19.add("c9f3f8d9-4660-4937-bc83-55cba005bf45");
        facultyList19.add("f307dba3-ba7f-4a73-8620-b6bb2bd267ff");
        facultyList19.add("0984fee1-2ecd-4ca2-8dd7-9c6b88386861");
        facultyList19.add("b2ca5c9e-ea7b-4c2f-91e1-b8de8d81c69a");
        facultyList19.add("44ceda40-86c9-4504-b53e-fbf238456288");
        facultyList19.add("226143e3-2850-4025-9438-1e77b884a000");
        facultyList19.add("770f49df-c44a-4a87-a00e-534006ffd9d6");
        facultyList19.add("da4ed86c-6681-47c9-aaef-5e22bbb0266d");
        facultyList19.add("b89f98de-830f-4281-8096-6c368ad39856");
        facultyList19.add("bb00cc68-a1e8-4211-a460-c726661c08df");
        facultyList19.add("f43af208-8c0f-40cc-8df5-2eebc9c1c946");
        facultyList19.add("e6fd4130-7f1d-4318-a8ac-8177da1627fd");
        facultyList19.add("e9111cb4-9828-4d54-8753-14aca93c4e3e");
        facultyList19.add("91ca349d-3a13-4882-8ae1-cbd74a8abf71");
        facultyList19.add("59ce1713-e2d6-460a-8124-f0a7f709247d");
        facultyList19.add("fe499ab4-ab8f-4429-9f9a-3219e87cec1f");
        facultyList19.add("5493754d-6d3f-4cbd-bea0-8b6990bce9bd");
        facultyList19.add("d0ee832b-170b-4980-ac86-41112b4a5f45");
        facultyList19.add("236a6d50-345c-4a6a-8961-2861b27f084d");
        facultyList19.add("6091ecd8-340f-4b46-81f4-2f34a7bfa177");
        facultyList19.add("f550bcf1-4eca-49b8-b539-05684beb6882");
        facultyList19.add("977c2254-0d11-4c2f-81f2-620350181c11");
        facultyList19.add("b39c362f-c87d-49c6-a39b-1ae1a45030ef");

        university19.setFacultyList(facultyList19);
        universityService.create(university19);


        //20. University of Melbourne
        UniversityCreateDTO university20 = new UniversityCreateDTO();
        university20.setName("University of Melbourne");
        university20.setWebSite("www.unimelb.edu.au");
        university20.setDescription("<p>The University of Melbourne is a public research university in Melbourne, Australia.</p>\n" +
                "<p>Having been established in 1853, it is the second oldest university in Australia and the oldest in the state of Victoria.</p>\n" +
                "<p>The main campus is located in the Melbourne suburb of Parkville with several other campuses located across Victoria. These include the campuses at Southbank, Burnley, Creswick, Dookie, Shepparton, Werribee.</p>\n" +
                "<p>The university is divided into 10 faculties including architecture, building and planning; arts; business and economics; education; engineering; fine arts and music; law; medicine, dentistry and health sciences; science and veterinary and agricultural sciences.</p>\n" +
                "<p>There is a large library system at the university, with libraries spread across the different campuses and some that cater to specific subjects such as the Brownless Biomedical Library and the Law Library.</p>\n" +
                "<p>A number of museums and art galleries are located across the university covering a number of different topics including medical history, zoological specimens, contemporary art, dental collections and anatomy and pathology.</p>\n" +
                "<p>There are many student clubs and associations at the University of Melbourne - many of which are associated with faculties and subject disciplines. There is also a diverse range of student communities. There are many sports clubs and teams that students can get involved with including athletics, badminton, cricket, hockey, tennis, ultimate frisbee volleyball, water polo and Quidditch.</p>\n" +
                "<p>Notable alumni include the former prime minister of Australia Julia Gillard, author and academic Germaine Greer, comedian Ronny Chieng and chef, restaurateur and food writer Stephanie Alexander. Seven Nobel Laureates have also taught in the institution.</p>");
        university20.setRating(20L);
        university20.setCountryId(50L);
        university20.setPhotoId("9c6494e6-98be-49f1-8e65-d8330246c05a");
        university20.setLogoId("9c6494e6-98be-49f1-8e65-d8330246c05a_logo");
        university20.setDegreeList(degreeTypeList);
        List<String> facultyList20 = new LinkedList<>();
        //Natural science faculties
        facultyList20.add("a42c3401-d82f-464c-8054-ad0449b92e8a");
        facultyList20.add("e146c49f-2633-4ca8-87b1-4eccf7b5f9f4");
        facultyList20.add("5ec62766-9129-4432-a881-dbe2be4a0b12");
        facultyList20.add("f17ad858-0935-4ccb-bd1d-ddb3a9d6950c");
        facultyList20.add("4db2f039-c576-4fa2-a08a-ebb5ddd62307");
        facultyList20.add("0cfa779a-97f4-445a-b9b8-44ec21f63e08");
        facultyList20.add("bb737b92-9a67-4a4b-a6a0-883489780ae2");
        facultyList20.add("f5f65c48-4587-4908-b790-481273fc231e");
        facultyList20.add("c993863e-946c-422c-a3c2-7e2af1414d23");
        facultyList20.add("62a4265e-e13f-4690-8fd7-ce3b9b545e20");
        facultyList20.add("ccb0734d-bacd-4b03-8f90-2b810101fa56");
        facultyList20.add("b2a94998-57b8-42bd-85c5-04573ffeeae4");
        facultyList20.add("08faf75a-9e76-406c-80ac-71c895129696");
        facultyList20.add("c8465d31-db8b-46f4-9a42-f1af9f4250f7");
        facultyList20.add("a6ac494b-45cf-4b79-bd81-8cdac14b71db");
        facultyList20.add("fdfe87ee-d240-49c2-9970-6c88b5c74a35");
        facultyList20.add("97f9110f-254e-4394-b42c-259c45aa0f5e");
        facultyList20.add("66f7908a-f801-4a60-bd43-6e3041aa1f0d");
        facultyList20.add("1fa4c796-85e1-4495-bfe0-dbffe04fea97");
        facultyList20.add("b7813475-4128-4e8d-940e-af9a706810c5");
        facultyList20.add("0ee51720-6b79-4651-bd04-87a7d990d966");
        facultyList20.add("93c7ab56-730f-40fd-adcf-4ac384ec3750");
        facultyList20.add("34f285b8-b23b-420c-9994-eaaf4c54ce9d");
        facultyList20.add("df275283-534b-403f-a702-8e6c84459d96");
        facultyList20.add("d07a2f29-1752-4a37-ba48-206bcc6b0d60");
        facultyList20.add("b38804ae-2977-412f-9e1b-6c91be968afa");
        facultyList20.add("5372847f-5b27-41a1-9f10-baa024d2e51b");
        facultyList20.add("30e3813f-6780-477a-b720-218956e3eb90");
        facultyList20.add("fa41bbf7-05f5-45d2-894b-00b9b7f1eb8e");
        facultyList20.add("daa31b19-8078-4f25-bac1-e580c372d65d");
        //Tourism & Hospitality
        facultyList20.add("a0a59796-66dc-4b73-8348-d9b8d850993d");
        facultyList20.add("1a494a3c-b357-4677-bbe5-9860feb2c4a6");
        facultyList20.add("33168d70-047c-4fc9-a22e-d04088599f4c");
        facultyList20.add("47b84f18-9850-45ff-9e84-66cef07b5730");
        facultyList20.add("98e3ed3f-bcdd-4fa8-bb4f-f30b2e5492ff");
        facultyList20.add("0f271253-0359-41a3-93ac-189a21b522e0");
        facultyList20.add("e98c77d2-929c-4f9c-a2a5-d2adf80be7c4");
        facultyList20.add("dd459c9d-4c2f-49ca-9534-7e7c21cf41ec");
        facultyList20.add("3bebd981-97b2-4517-8b8e-ea2f571af591");
        facultyList20.add("b26d410c-13dc-4588-9aff-3be4e47fad95");
        facultyList20.add("effe2357-45e9-45d3-8128-a4f6ba2b2057");
        facultyList20.add("fa78cfac-9452-419e-a3a9-1824b3c620af");
        facultyList20.add("29623dfa-5ec1-4357-8c5e-fb56f6b506be");
        facultyList20.add("7c7fb5f3-8a8c-4973-a927-c34862b94595");
        university20.setFacultyList(facultyList20);
        universityService.create(university20);
    }


}
