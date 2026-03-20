from django.contrib import admin
from .models import Choice, Question

class ChoiceInline(admin.TabularInline):
    model = Choice
    extra = 3

class QuestionAdmin(admin.ModelAdmin):
    # Поля и группировка
    fieldsets = [
        (None, {"fields": ["question_text"]}),
        ("Date information", {"fields": ["pub_date"], "classes": ["collapse"]}),
    ]
    # Добавление ответов внутри вопроса
    inlines = [ChoiceInline]
    
    # Настройка списка всех вопросов
    list_display = ["question_text", "pub_date", "was_published_recently"]
    list_filter = ["pub_date"] # Фильтр справа
    search_fields = ["question_text"] # Поиск сверху

admin.site.register(Question, QuestionAdmin)