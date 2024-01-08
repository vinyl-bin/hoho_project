package hoho_project.hoho.service;

import hoho_project.hoho.domain.Home;
import hoho_project.hoho.domain.Inquiry;
import hoho_project.hoho.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    //사진 등록
    @Transactional
    public Inquiry saveInquiry(String inquiry_type, String inquiry_imgName, byte[] inquiry_image) {
        Inquiry inquiry = Inquiry.createInquiry(inquiry_type, inquiry_imgName, inquiry_image);

        inquiryRepository.save(inquiry);

        return inquiry;
    }

    //사진 삭제
    @Transactional
    public Inquiry deleteInquiry(Inquiry inquiry) {

        inquiryRepository.delete(inquiry);

        return inquiry;
    }

    public Inquiry findOne(Long inquiry_id) {
        return inquiryRepository.findOne(inquiry_id);
    }

    public List<Inquiry> findInquiries() {
        return inquiryRepository.findAll();
    }
}
