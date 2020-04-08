package com.skillbox.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseInitDto {

  final String title = "DevPub";
  final String subtitle = "Рассказы разработчиков";
  final String phone = "+7 495 720 25 17";
  final String email = "deal@symbioway.ru";
  final String copyright = "Даниил Пилипенко";
  final String copyrightFrom = "2020";
}