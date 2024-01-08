package hoho_project.hoho.service;

import hoho_project.hoho.domain.Inquiry;
import hoho_project.hoho.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
