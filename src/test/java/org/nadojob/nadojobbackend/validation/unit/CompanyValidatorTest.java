package org.nadojob.nadojobbackend.validation.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nadojob.nadojobbackend.exception.CompanyNameAlreadyExistsException;
import org.nadojob.nadojobbackend.repository.CompanyRepository;
import org.nadojob.nadojobbackend.validation.CompanyValidator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyValidatorTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyValidator companyValidator;

    @Test
    @DisplayName("Не выбрасывает, если название компании уникально")
    void uniqueCompanyName() {
        when(companyRepository.existsByName("Acme Corp")).thenReturn(false);
        assertDoesNotThrow(() -> companyValidator.validateCompanyNameDuplicate("Acme Corp"));
    }

    @Test
    @DisplayName("Выбрасывает, если название компании уже существует")
    void duplicateCompanyName() {
        when(companyRepository.existsByName("Acme Corp")).thenReturn(true);
        assertThrows(CompanyNameAlreadyExistsException.class,
                () -> companyValidator.validateCompanyNameDuplicate("Acme Corp"));
    }
}

