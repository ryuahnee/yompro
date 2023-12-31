package com.yumpro.ddogo.admin.service;

import com.yumpro.ddogo.admin.domain.UserDTO;
import com.yumpro.ddogo.admin.exception.DataNotFoundException;
import com.yumpro.ddogo.admin.repository.UserListRepository;
import com.yumpro.ddogo.admin.repository.UserModiRepository;
import com.yumpro.ddogo.admin.validation.UserModiForm;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.qna.service.QnaService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserListService {
    private final JavaMailSender mailSender;
    private final UserListRepository userRepository;
    private final UserModiForm userModiForm;
    private final UserModiRepository userModiRepository;

    public UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_no(user.getUser_no());
        userDTO.setUser_name(user.getUser_name());
        userDTO.setUser_id(user.getUserId());
        userDTO.setBIRTH(user.getBirth());
        userDTO.setPwd(user.getPwd());
        userDTO.setEmail(user.getEmail());
        userDTO.setGender(user.getGender());
        userDTO.setJoin_date(user.getJoinDate());

        return userDTO;
    }

    public List<UserDTO> userList(Map<String,Object> map) {
        return userRepository.userList(map);
    }

    public User getUser(int userNo){
        Optional<User> user =userModiRepository.findById(userNo);
        if(user.isPresent()) {
            return user.get();
        } else {
            //사용자 정의 예외처리
            throw new DataNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(User user, UserModiForm userModiForm) {
        boolean emailDuplicate = false;
        if(!user.getEmail().equals(userModiForm.getEmail())){     //기존 이메일값, 변경한 이메일값이 다르면
            emailDuplicate = userModiRepository.existsByEmail(userModiForm.getEmail());   //회원의 이메일값과 비교를 한다.
        }
        return emailDuplicate;
    }

    @Transactional(readOnly = true)
    public boolean checkIdDuplication(User user, UserModiForm userModiForm) {
        boolean user_ifDuplicate = false;
        if(!user.getUserId().equals(userModiForm.getUser_id())){     //기존 이메일값, 변경한 이메일값이 다르면
            user_ifDuplicate = userModiRepository.existsByUserId(userModiForm.getUser_id());   //회원의 이메일값과 비교를 한다.
        }
        return user_ifDuplicate;
    }
    @Transactional
    public void userModify(User user, UserModiForm userModiForm) {
        user.setUserId(userModiForm.getUser_id());
        user.setUser_name(userModiForm.getUser_name());
        user.setBirth(userModiForm.getBIRTH());
        user.setGender(userModiForm.getGender());
        user.setEmail(userModiForm.getEmail());
        userModiRepository.save(user);
    }


    public void sendSimpleEmail(String toEmail, String subject, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(text, true); // true를 설정하여 HTML 내용을 허용합니다.
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findUserByUserId(String userId) {
        Optional<User> user =userModiRepository.findByUserId(userId);
        return user;
    }

    public void deleteUser(User user) {
        userModiRepository.delete(user);
    }

    public UserModiForm toModifyForm(User user) {
        UserModiForm modiAdmin = new UserModiForm();
        modiAdmin.setUser_no(user.getUser_no());
        modiAdmin.setUser_name(user.getUser_name());
        modiAdmin.setUser_id(user.getUserId());
        modiAdmin.setBIRTH(user.getBirth());
        modiAdmin.setGender(user.getGender());
        modiAdmin.setEmail(user.getEmail());
        modiAdmin.setJoin_date(user.getJoinDate());
        return modiAdmin;
    }

    public int getUserListCount(Map<String, Object> map) {
        return userRepository.getUserListCount(map);
    }
}