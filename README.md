# 감정분석AI를 기반으로 한 나만의 맛집지도 웹 사이트! '또 갈 지도' 

 <div align="center">
<img width="80%" alt="스크린샷 2023-11-02 오전 3 26 17" src="https://github.com/ryuahnee/yompro/assets/135402890/2f49a241-60ad-4f23-82c8-3b4ce76e7dd6">
</div>

<br>

* 홈페이지 바로가기 : https://yomprogo.com <br>
* ID: test / PW: 1234
---

## 🖥️ 프로젝트 소개

- **‘또 갈지도’** 는 다른 사람에게는 알려주지 않고 나만 알고 싶은 맛집, 내가 남긴 리뷰만으로 구성된 나만의 맛집 지도 서비스 입니다.
- 후기를 남기면 감정분석 AI가 후기를 분석해 점수를 측정합니다. 재방문 여부도 같이 체크할 수 있습니다.
- 메인화면에서 재방문 여부를 기준으로 이달의 맛집 랭킹과 지역별 맛집 랭킹을 확인 할 수 있으며, 후기 감정분석 점수를 온도로 표시해 보여줍니다.
- 랭킹에서는 익명으로 된 다른 사용자들의 후기를 확인 할 수 있습니다.

<br>

## 🗓️ 프로젝트 상세

* 기간 : 2023.08.17 ~ 09.11

* 인원 : 5명 (기술팀장)

* 분류 : Spring Boot 기반 팀 프로젝트

* 언어 : Java(JDK 17), Spring Boot, Javascrip, JPA, Ajax, HTML5/CSS
* 서버 : Apache Tomcat 9.0, AWS EC2
* DB : MySQL 8.0 (RDS)
* API & library : Spring Security, KaKao Maps API, CLOVA Sentiment API, Scheduler API, Mail API, Thymeleaf 

## 💡 주요 역할 
- Spring Security (로그인/회원가입 구현 & 권한별 접근 처리)
- AWS EC2 배포 및 커스텀 도메인 연결
- 에러페이지처리

---

## ❕신경쓴 코드
* 스프링시큐리티 권한별 접근 설정 (class SecurityConfig)
<pre><code>@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeHttpRequests()
                .and()
                .sessionManagement()
                .maximumSessions(1) 
                .maxSessionsPreventsLogin(false)    // 2중 로그인 방지
                .expiredUrl("/user/login"); 
        http.authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/user/modifyForm/**")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/mymap/**")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/user/joinForm")).denyAll() //로그인 후 회원가입접근불가
                .anyRequest().permitAll()
                .and().formLogin().loginPage("/user/login").usernameParameter("user_id").passwordParameter("pwd").defaultSuccessUrl("/")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/").invalidateHttpSession(true);

        return http.build();
    }
</code></pre>
* admin page 접근 설정
<pre><code>// 현재 사용자의 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 현재 사용자의 권한 중 하나라도 "ROLE_ADMIN"이 아니라면
        if (!authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"권한이 없습니다.");
        }
</code></pre>

#### ‣ [커밋히스토리 보러가기](https://github.com/ryuahnee/yompro/commits/user2)

---

