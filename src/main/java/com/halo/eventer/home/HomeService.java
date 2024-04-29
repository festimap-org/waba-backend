package com.halo.eventer.home;


import com.halo.eventer.home.HomeDto;
import com.halo.eventer.user.dto.MissingPersonPopupDto;
import com.halo.eventer.notice.dto.BannerResDto;
import com.halo.eventer.user.dto.UrgentDto;
import com.halo.eventer.user.repository.MissingPersonRepository;
import com.halo.eventer.notice.repository.NoticeRepository;
import com.halo.eventer.user.repository.UrgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final NoticeRepository noticeRepository;
    private final MissingPersonRepository missingPersonRepository;
    private final UrgentRepository urgentRepository;

    public HomeDto getMainPage() {
        List<BannerResDto> banner = noticeRepository.findAllByPicked(true).stream().map(o->new BannerResDto(o)).collect(Collectors.toList());
        List<MissingPersonPopupDto> missingPerson = missingPersonRepository.findAllByPopup(true).stream().map(o->new MissingPersonPopupDto(o)).collect(Collectors.toList());
        List<UrgentDto> urgent = urgentRepository.findAllByPopup(true).stream().map(o->new UrgentDto(o)).collect(Collectors.toList());

        HomeDto homeDto = new HomeDto(banner,missingPerson,urgent);
        return homeDto;
    }



}
