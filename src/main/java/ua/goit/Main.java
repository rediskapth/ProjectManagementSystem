package ua.goit;

import ua.goit.config.DatabaseManager;
import ua.goit.config.PostgresHikariProvider;
import ua.goit.config.PropertiesUtil;
import ua.goit.model.convert.CompaniesConverter;
import ua.goit.model.convert.CustomersProjectsConverter;
import ua.goit.model.convert.DevelopersConverter;
import ua.goit.model.dao.CompaniesDao;
import ua.goit.model.dto.CompaniesDto;
import ua.goit.model.dto.CustomersProjectsDto;
import ua.goit.repository.CompaniesRepository;
import ua.goit.repository.CustomersProjectsRepository;
import ua.goit.repository.Repository;
import ua.goit.service.CompaniesService;
import ua.goit.service.CustomersProjectsService;
import ua.goit.service.command.CommandService;

public class Main {
    public static void main(String[] args) {

        PropertiesUtil util = new PropertiesUtil();
        DatabaseManager dbConnector = new PostgresHikariProvider(util.getHostname(), util.getPort(), util.getSchema(),
                util.getUser(), util.getPassword());

        Repository<CompaniesDao> companiesRepository = new CompaniesRepository(dbConnector);
        CompaniesConverter companiesConverter = new CompaniesConverter();
        DevelopersConverter developersConverter = new DevelopersConverter();
        CompaniesService companiesService = new CompaniesService(companiesConverter, companiesRepository);
        CompaniesDto companiesDto = new CompaniesDto();

//        companiesDto.setCompanyId(8);
//        companiesDto.setName("Company_8");
//        companiesDto.setLocation("Some city_2");
//        companiesService.save(companiesDto);

        System.out.println(companiesService.findById(1));

//        companiesService.remove(companiesDto);
//        companiesService.update(companiesDto);

        CustomersProjectsRepository customersProjectsRepository = new CustomersProjectsRepository(dbConnector);
        CustomersProjectsConverter customersProjectsConverter = new CustomersProjectsConverter();
        CustomersProjectsService customersProjectsService = new CustomersProjectsService(customersProjectsConverter, customersProjectsRepository);
        CustomersProjectsDto customersProjectsDto = new CustomersProjectsDto();

        customersProjectsDto.setCustomerId(1);
        customersProjectsDto.setProjectId(1);

//        customersProjectsService.save(customersProjectsDto);

        CustomersProjectsDto customersProjectsDtoNew = new CustomersProjectsDto();
        CustomersProjectsDto customersProjectsDtoOld = new CustomersProjectsDto();

        customersProjectsDtoNew.setCustomerId(1);
        customersProjectsDtoNew.setProjectId(2);

        customersProjectsDtoOld.setCustomerId(1);
        customersProjectsDtoOld.setProjectId(1);

//        customersProjectsService.update(customersProjectsDtoNew, customersProjectsDtoOld);
//        customersProjectsService.remove(customersProjectsDtoNew);
        System.out.println(customersProjectsService.findByProjectId(3));
        System.out.println(customersProjectsService.findByCustomerId(3));

        CommandService commandService = new CommandService(dbConnector, developersConverter);

        System.out.println(commandService.findAllDev(1));
        System.out.println(commandService.findSumSalary(1));
        System.out.println("Java Developers - " + commandService.findJavaDevelopers());
        System.out.println("Middle Developers - " + commandService.findMiddleDevelopers());
        System.out.println("=====================");
        System.out.println(commandService.findProjectsDateWithNumberOfDevelopers());
    }
}
