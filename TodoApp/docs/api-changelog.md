# API Değişiklik Geçmişi

Bu dosya TodoApp API'sinde yapılan geriye dönük uyumluluk etkisi olan değişiklikleri kayıt altına alır.

## Changelog

- 2026-10-06 [OTO-2] § Todo Model — `date` alanı eklendi (LocalDateTime, oluşturma zamanı otomatik set ediliyor, JSON formatı: `dd.MM.yyyy HH:mm:ss`)
- 2026-10-06 [OTO-2-review] § Validation — Todo model'e @NotBlank validation eklendi (userId, description), editTodo null check eklendi
