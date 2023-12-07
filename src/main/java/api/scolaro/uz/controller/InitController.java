package api.scolaro.uz.controller;

import api.scolaro.uz.dto.university.UniversityCreateDTO;
import api.scolaro.uz.enums.UniversityDegreeType;
import api.scolaro.uz.service.UniversityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public void initUniversity() {
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
        university1.setCountryId(2L);
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

        // 2. Oxford

    }


}
