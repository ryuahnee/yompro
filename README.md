# 감정분석AI를 기반으로 한 나만의 맛집지도 웹 사이트! '또 갈 지도' 

 <div align="center">
<img width="336" alt="또갈지도 메인" src="https://github.com/ryuahnee/yompro/assets/135402890/db1e3e10-239e-4b4a-b13e-fe1bbd5f4afb">
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

* 주요 역할 : Spring Security, 로그인/회원가입, Email-API, 에러페이지처리, AWS EC2 배포 및 커스텀 도메인 연결

* 사용 기술 : Java(JDK 17), Spring Boot, Javascrip, JPA, Ajax, HTML5/CSS

---

## 신경쓴 코드
* 스프링시큐리티 권한별 접근 설정
<pre><code>
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf().disable() 
                .authorizeHttpRequests()
                .and()
                .sessionManagement()
                .maximumSessions(1) //최대 세션 허용 수
                .maxSessionsPreventsLogin(false)    // 2중 로그인 방지 -> 먼저로그인한 user가 튕긴다.
                .expiredUrl("/user/login");         // 튕겨지면 user/login페이지로 이동
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
}
</code></pre>


#### ‣ [커밋히스토리 보러가기](https://github.com/ryuahnee/yompro/commits/user2)

---

