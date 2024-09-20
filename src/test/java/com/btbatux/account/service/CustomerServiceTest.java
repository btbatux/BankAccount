package com.btbatux.account.service;

import com.btbatux.account.dto.CustomerDto;
import com.btbatux.account.dto.converter.CustomerDtoConverter;
import com.btbatux.account.exception.CustomerNotFoundException;
import com.btbatux.account.model.Customer;
import com.btbatux.account.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private CustomerDtoConverter customerDtoConverter;


    @BeforeEach
    public void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerDtoConverter = Mockito.mock(CustomerDtoConverter.class);
        customerService = new CustomerService(customerRepository, customerDtoConverter);
    }

    /*
     * customerId verildiğinde, bir Customer nesnesi döndürmelidir.
     * Burada, customerId mevcut olduğunda çalış.
     */
    @Test
    public void testFindCustomerById_whenCustomerIdExists_shouldReturnCustomer() {
        // Testte kullanılacak bir müşteri nesnesi oluşturuluyor.
        // Bu, "id", "name", "surname" ve boş bir "Set" içeren bir Customer nesnesi.
        Customer customer = new Customer("id", "name", "surname", Set.of());

        // Mockito kullanarak customerRepository'nin findById() metodunun davranışı taklit ediliyor.
        // Bu metot, "id" verildiğinde, yukarıda oluşturulan customer nesnesini içeren Optional döndürecek.
        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

        // Test edilen metod çağrılıyor: customerService.findCustomerById("id")
        // Bu, yukarıdaki mock'lanmış repository üzerinden çalıştırılıyor.
        Customer result = customerService.findCustomerById("id");

        // Sonuç olarak dönen customer nesnesinin, beklenen customer nesnesiyle aynı olup olmadığını kontrol ediyor.
        // assertEquals metodu ile iki nesnenin eşit olduğu doğrulanıyor.
        assertEquals(result, customer);
    }


    @Test
    public void testFindCustomerById_whenCustomerIdDoesNotExists_shouldThrowCustomerNotFoundException() {
        // customerRepository.findById("id") çağrıldığında boş bir Optional dönecek şekilde davranışı taklit ediyoruz.
        // Bu, belirtilen "id" ile bir müşteri bulunmadığını simüle eder.
        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        // assertThrows metodu, customerService.findCustomerById("id") çağrıldığında
        // CustomerNotFoundException istisnasının fırlatıldığını doğrular.
        // Eğer istisna fırlatılmazsa test başarısız olur.
        assertThrows(CustomerNotFoundException.class, () -> customerService.findCustomerById("id"));
    }


    @Test
    public void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomer() {
        // Bir Customer nesnesi oluşturuyoruz. Bu, veritabanından bulunacak müşteri objesini temsil ediyor.
        Customer customer = new Customer("id", "name", "surname", Set.of());

        // CustomerDto nesnesi oluşturuyoruz. Bu, Customer nesnesinin DTO'ya dönüştürülmüş hali.
        CustomerDto customerDto = new CustomerDto("id", "name", "surname", Set.of());

        // customerRepository.findById("id") çağrıldığında, Optional.of(customer) dönecek şekilde mock davranışı ayarlanıyor.
        // Yani, "id" ile ilgili bir müşteri bulunduğunu simüle ediyoruz.
        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

        // customerDtoConverter.convertToCustomerDto(customer) çağrıldığında,
        // customerDto dönecek şekilde mock davranışı ayarlanıyor.
        // Bu, customer nesnesinin DTO'ya başarılı şekilde dönüştürüldüğünü simüle eder.
        Mockito.when(customerDtoConverter.convertToCustomerDto(customer)).thenReturn(customerDto);

        // customerService.getCustomerById("id") çağrıldığında, bu metodun customerDto döndürmesini bekliyoruz.
        CustomerDto result = customerService.getCustomerById("id");

        // assertEquals ile beklenen ve gerçek değerleri karşılaştırıyoruz.
        // result'ın beklediğimiz customerDto ile aynı olup olmadığını kontrol ediyoruz.
        assertEquals(result, customerDto);
    }


    @Test
    public void testGetCustomerById_whenCustomerIdDoesNotExists_shouldThrowCustomerNotFoundException() {
        // customerRepository.findById("id") çağrıldığında, Optional.empty() dönecek şekilde mock davranışı ayarlanıyor.
        // Bu, veritabanında "id" ile ilgili bir müşteri bulunamadığını simüle eder.
        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        // assertThrows ile customerService.getCustomerById("id") çağrıldığında,
        // CustomerNotFoundException istisnasının atıldığını doğrularız.
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById("id"));

        // Mockito.verifyNoInteractions ile customerDtoConverter ile hiçbir etkileşim olmadığını doğrularız.
        // Yani, eğer müşteri bulunamazsa, DTO dönüşümü yapılmamalıdır.
        Mockito.verifyNoInteractions(customerDtoConverter);
    }

}