package pro.sky.telegrambot.shelter.model;

import javax.swing.plaf.PanelUI;

/**
 * Class contains all constants with information about shelter and animals
 *  @version $Revision: 1 $
 */
public final class Info {
    public static final String WELCOME_MESSAGE = "Привет!\uD83D\uDC4B \n" +
            "Это бот поддержки приюта для животных \"Три столицы\".\n" +
            "Здесь вы можете узнать информацию о приюте и посмотреть наших питомцев.\n" +
            "Если Вам нужна помощь волонтера, то нажмите:\u2753Позвать волонтера";

    public static final String SCHEDULE_AND_ADDRESS = "\n\uD83D\uDD50 Режим работы: С понедельника по пятницу, с 13:00 до 21:00"+
            "\n"+
            "\uD83C\uDFE2 Адрес: Санкт-Петербург, пр. Большой Смоленский, д. 7-9, лит. А";

    public static final String SHELTER_INFO = "Мы - некоммерческая организация «Приют для животных»\n«Три столицы».\n" +
            "C 2008 года мы спасаем бездомных кошек и собак, заботимся о них, ищем им дом.\n" +
            "Мы содержим приют, в котором живут около 20 собак и 10 кошек.\n" +
            "При поступлении в приют на каждое животное заводится личная карточка, куда вносятся данные о вакцинации, стерилизации, причинах поступления в приют, " +
            "данные о физическом состоянии и характере, ставится отметка с датой передачи в семью.\n" +
            "Все животные в приюте круглосуточно находятся под наблюдением сотрудников и ветеринарных врачей.";

    public static final String RULES = "\uD83D\uDCD5 Правила знакомства:\n" +
            "\n" +
            "\uD83D\uDD38Понимание обязательств: \nПрежде чем забрать животное из приюта, убедитесь, что вы полностью осознаете свои обязанности и готовы взять на себя ответственность за заботу о нем на протяжении всей его жизни.\n" +
            "\n" +
            "\uD83D\uDD38Исследуйте породу и особенности: \nПознакомьтесь с породой животного и его особенностями, чтобы быть уверенным, что оно подходит вам по характеру, потребностям и образу жизни.\n" +
            "\n" +
            "\uD83D\uDD38Участие в программе адопции: \nПриюты зачастую требуют предварительное участие в программе адопции, которая включает заполнение анкеты и обсуждение вашей способности предоставить достойный дом для животного.\n" +
            "\n" +
            "\uD83D\uDD38Визит в приют: \nПеред тем, как принять окончательное решение, посетите животное в приюте. Возможно, вам будет предоставлена возможность познакомиться с ним, погулять, поговорить с сотрудниками приюта или провести время с ним в игровой комнате. Это позволит вам получить представление о его поведении и характере.\n" +
            "\n" +
            "\uD83D\uDD38Вопросы и обсуждение: \nЗадавайте вопросы сотрудникам приюта или ветеринару, связанные с здоровьем, поведением или особенностями животного. " +
            "Обсудите его рацион питания, лечение от блох и глистов, возможные проблемы со здоровьем и ветеринарное обслуживание.\n" +
            "\n" +
            "\uD83D\uDD38Организуйте пробежку или прогулку: \nЕсли возможно, устройте прогулку с животным или проведите с ним время на просторе. " +
            "Это поможет вам оценить его энергичность и социальность.\n" +
            "\n" +
            "\uD83D\uDD38Сопроводительное руководство: \nПросите сотрудников приюта поделиться информацией о прошлой жизни животного, его привычках, здоровье и особенностях. " +
            "Это поможет вам лучше понять и настроиться на нового члена семьи.\n" +
            "\n" +
            "\uD83D\uDD38Семейное решение: \nОбсудите и согласуйте решение о взятии животного с членами своей семьи или сожителями, чтобы все были готовы к новому питомцу.\n" +
            "\n" +
            "\uD83D\uDD38Подготовьте дом: \nПрежде чем привести животное домой, убедитесь, что дом и среда в нем безопасны и подготовлены для приема нового животного. " +
            "Установите границы и создайте место для отдыха, игр и питания.\n" +
            "\n" +
            "\uD83D\uDD38Постепенная адаптация: \nОжидайте, что животное будет нуждаться во времени для приспособления к новому дому и семье. " +
            "Уделите ему достаточно внимания, заботы и терпения для создания у него комфортной атмосферы и привязанности к вам.";

    public static final String DOCS = "\uD83D\uDCC4 Список документов, необходимых для того, чтобы взять животное из приюта:\n" +
            "\n" +
            "1. Документ, удостоверяющий личность: Паспорт или другой идентификационный документ, удостоверяющий вашу личность и возраст.\n" +
            "\n" +
            "2. Заявка на адопцию: Возможно, вам придется заполнить заявку на адопцию, в которой вы предоставите свои личные данные, мотивацию для взятия питомца, предыдущий опыт владения животными и другую информацию.\n" +
            "\n" +
            "3. Договор адопции: Вам могут потребовать подписание договора адопции, где будут установлены условия и правила по уходу за животным, а также обязательства, которые вы берете на себя. Читайте и понимайте договор перед его подписанием.\n" +
            "\n" +
            "4. Справка от ветеринара: Приют может запросить справку от вашего ветеринара, подтверждающую, что вы в состоянии обеспечить заботу и надлежащее медицинское обслуживание для животного.\n" +
            "\n" +
            "5. Свидетельство о постоянном месте жительства: В некоторых случаях вам может потребоваться предоставление свидетельства о вашем постоянном месте жительства для подтверждения, что вы можете обеспечить достойные условия для питомца.\n" +
            "\n" +
            "6. Оплата адопционного сбора: В приютах обычно взимается адопционный сбор для покрытия расходов на уход, медицинское обслуживание и другие нужды животного. Уточните сумму и способы оплаты в приюте.\n" +
            "\n" +
            "7. Личная встреча и согласование: Возможно, вам потребуется пройти личную встречу с представителями приюта или провести обязательное согласование, чтобы убедиться, что вы чувствуете себя комфортно с животным и готовы взять его домой.\n" +
            "\n" +
            "Важно отметить, что эти требования могут различаться, поэтому лучше связаться с выбранным приютом и узнать о конкретных документах и процедуре, необходимых для адопции животного.";

        public static final String RECOMMENDATIONS = "\uD83D\uDD16 Рекомендации:\n"+
                "\n" +
                "\uD83D\uDE8C Правила перевоза животных разными видами транспорта:\n" +
                "Для разных видов транспорта существуют свои правила перевозки животных.\n" +
                "Проезд животных регламентируется определенными правилами, которые следует соблюдать владельцу. " +
                "Мы собрали для вас правила для всех видов транспорта: наземного, метро, личного автомобиля и самолета.\n" +
                "\uD83D\uDCD5 Не пренебрегайте ими, чтобы потом у вас не возникло проблемы с проездом животных в транспорте!\n" +
                "\n" +
                "\uD83D\uDC36 Как обустроить дом, если вы решили завести щенка или котенка?\n" +
                "Успешная подготовка к появлению питомца начинается с небольшого приготовления к первым нескольким дням совместной жизни с домашним любимцем.\n" +
                "\n" +
                "\uD83C\uDFD8 Обустройства дома для взрослого животного. Думаете завести кошку, собаку, морскую свинку или хомячка?\n" +
                "\uD83D\uDCD5 Существует 4 важных правила по уходу за животными, которых нужно придерживаться хозяевам:\n" +
                "1. Регулярно наблюдайте за здоровьем и питанием питомца.\n" +
                "2. Следите за гигиеной животного и его жилища.\n" +
                "3. Гуляйте и играйте с любимцем.\n" +
                "4. Обустройте питомцу собственный уголок.\n" +
                "\n" +
                "\uD83D\uDD4A Бывают ситуации, когда из-за врожденных особенностей, болезни или травмы, собака становится инвалидом. Может показаться, что жизнь такого питомца будет полна страданий, но это заблуждение. Если собака не испытывает болей, а хозяин готов ухаживать и помогать собаке адаптироваться к новой жизни, то она как правило и не замечет неудобств, связанных с особенностями ее здоровья. К таким животным нужен особенный подход.";

        public static final String SAVE_INSTRUCTIONS =  "\uD83E\uDDBA Находясь на территории приюта, пожалуйста, соблюдайте наши правила и технику безопасности!\n" +
                "\n"+
                "Запрещается:\n" + "\uD83D\uDD12 Самостоятельно открывать выгулы и вольеры без разрешения работника приюта.\n" +
                "\uD83C\uDF2D Кормить животных.\n" + "Этим Вы можете спровоцировать драку. Угощения разрешены только постоянным опекунам и волонтерам, во время прогулок с животными на поводке.\n" +
                "\uD83D\uDDD1 Оставлять после себя мусор на территории приюта и прилегающей территории.\n" +
                "\uD83D\uDED1 Подходить близко к вольерам и гладить собак через сетку на выгулах. Животные могут быть агрессивны!\n" +
                "\uD83D\uDE3F Кричать, размахивать руками, бегать между будками или вольерами, пугать и дразнить животных.\n" +
                "\uD83D\uDC6A Посещение приюта для детей дошкольного и младшего школьного возраста без сопровождения взрослых.\n" +
                "\uD83C\uDF7E Посещение приюта в состоянии алкогольного, наркотического опьянения.";

        public static final String REASONS_FOR_REJECTION = "\uD83D\uDED1 Мы можем отказаться доверить Вам животное по нескольким причинам:\n" +
                "1. Отказ обеспечить обязательные условия безопасности питомца на новом месте\n" +
                "2. Нестабильные отношения в семье, в которую хотят забрать питомца\n" +
                "3. Наличие дома большого количества животных\n" +
                "4. Маленькие дети в семье (Мы не против детей, но при выборе семьи приоритетом является польза для животных, многие из которых с трудом социализировались после психотравм)\n" +
                "5. Аллергия на шерсть\n" +
                "6. Животное забирают в подарок кому-то\n" +
                "7. Животное забирают в целях использования его рабочих качеств\n" +
                "8. Отсутствие регистрации и собственного жилья или его несоответствие нормам приюта";

        public static final String TIPS_FOR_DOG_HANDLER = "\uD83D\uDC36 Советы кинолога по общению с собакой:\n" +
                "\n" +
                "\ud83d\ude0d 1. Время вместе.\n" +
                "Чем больше вы будете проводить время с собакой, тем крепче станет ваша эмоциональная связь. Не стоит ограничиваться одними прогулками - животному нужно больше вашего внимания.\n" +
                "\n" +
                "Проводите совместные игры, ухаживайте за псом, чтобы он увидел, что интересен вам всегда.\n" +
                "\n" +
                "\ud83d\ude35 2. Не бейте.\n" +
                "Животное никогда не будет воспринимать побои в качестве метода воспитания: насилие может породить страх, но никак не любовь. Страх никогда не заставит собаку быть вам преданной.\n" +
                "\n" +
                "Воспитывать пса нужно только через позитивные методы: дать вкусняшку за хорошее поведение или отругать строгим голосом за плохое.\n" +
                "\n" +
                "\ud83d\ude2d 3. Уважайте чувства.\n" +
                "Животное может испытывать разные эмоции - страх, любопытство, обиду, радость, злость. Не стоит обесценивать их чувства - собака может вполне обоснованно на вас обидеться.\n" +
                "\n" +
                "Даже если вы сильно устали, то не стоит прогонять любимца: уделите ему хотя бы 5 минут.\n"+
                "\n" +
                "\ud83d\ude33 4. Нормальные прогулки.\n" +
                "Животное может отправиться на улицу только с вами, поэтому радостно ждет каждой прогулки. Ведите себя на улице адекватно: если пес хочет изучить территорию, то не стоит его грубо одергивать. В конце концов, это просто его инстинкты, а не желание вам досадить.\n" +
                "\n" +
                "Выбирайтесь хотя бы в выходные на природу, чтобы пес мог вдоволь насладиться свободой и свежим воздухом.\n" +
                "\n" +
                "\ud83d\udd70 5. Организованность.\n" +
                "Собаки любят, когда все жизненные события происходят по расписанию: еда появляется в одно и то же время, на улицу вы ходите тоже в определенные часы.\n" +
                "\n" +
                "Благодаря такому подходу животное поймет, что вам можно доверять, и вам удастся выстроить нормальные взаимоотношения.";

        public static final String RECS_FOR_PROVEN_DOG_HANDLER = "\ud83d\udd76 По каким критериям нужно выбирать кинолога?\n"+
                "\n" +
                "\ud83e\udd14 1. Образование.\n" +
                "Хороший кинолог – это не просто человек, который любит собак. Он должен отлично разбираться в психологии животных, уметь считывать язык тела, знать анатомию и физиологию, разбираться в породах. " +
                "Такой специалист обязательно предложит несколько способов решения задач, всегда будет мотивировать собаку и никогда не будет принуждать к занятиям.\n" +
                "\n" +
                "\ud83d\ude35 2. Методика обучения.\n" +
                "Грамотный специалист всегда расскажет, что и зачем он делает, при этом используя понятный и доступный язык для объяснения принципов своей работы. " +
                "Обратите внимание на то, как реагирует на кинолога собака, комфортно ли ей работать с ним. Важно, чтобы к питомцу не применяли силу и не заставляли работать насильно. " +
                "Методы, основанные на принуждении и насилии, сделают собаку запуганной и пассивной, сформируют страх и отвращение к занятиям. Таких методов надо избегать.\n" +
                "\n" +
                "\ud83d\ude14 3. Опыт.\n" +
                "Не нужно полагаться только на опыт, но и недооценивать его тоже не стоит. Следует поинтересоваться, как долго специалист работает с собаками. " +
                "Можно поискать отзывы о работе кинолога и рекомендации других владельцев собак. Безусловно, не нужно оценивать работу специалиста только по отзывам из интернета.\n" +
                "\n" +
                "\ud83d\ude44 4. Цели.\n" +
                "Выбирая специалиста, необходимо учитывать цели. Этот фактор может быть решающим при выборе специалиста, поскольку будет учитываться опыт и специализация каждого конкретного кинолога. " +
                "От того, что необходимо получить в итоге, зависит выбор.\n";

        public static final String REASONS_FOR_REFUSAL = "\ud83d\ude1f Причины отказа приютить питомца: \n" +
                "\n" +
                "1. Большое количество животных дома.\n" +
                "\n" +
                "2. Нестабильные отношения в семье.\n" +
                "\n" +
                "3. Наличие маленьких детей.\n" +
                "\n" +
                "4. Съемное жилье.\n" +
                "\n" +
                "5. Животное в подарок или для работы.\n" +
                "\n" +
                "\ud83d\udcaf Каждый критерий важен \ud83d\udcaf";

        public static final String CONTACTS =  "\u260E Телефон: +7 999 77 88 999\n" +
                "\uD83E\uDEAA Оформить пропуск на машину по номеру: +7 999 65 36 647";

        public static final String ANIMALS = "В нашем приюте живут как кошки, так и собаки.\nВыберите, с кем вы хотите познакомиться!";
        public static final String HELP_START = "\u2753 Чтобы волонтер с вами связался:\n1. Укажите свой логин: @...\n2. (С красной строки)\nОпишите ваш вопрос\u2753\n";
        public static final String HELP_CONFLICT = "\u2753\u2753\u2753 Возникла ошибка\n(Оформление не соответствует)\n" +
                "\n1. Сначала выберите раздел:\n       \u2753 Позвать волонтера\n2. Запишите согласно образцу:\n@UserName\n(Новая строка) Ваш вопрос\n";
        public static final String HELP_END = "\uD83D\uDD4A Волонтер с вами свяжется,\nожидайте \uD83D\uDD50";
        public static final String REPORT_FORM = "Дорогой усыновитель, пожалуйста, пришли отчет в следующей форме:\n" +
                "Отчет:\n1. Рацион животного.\n" +
                "2. Общее самочувствие и привыкание к новому месту.\n" +
                "3. Изменение в поведении: отказ от старых привычек, приобретение новых.\n" +
                "Обязательно прикрепи фото животного, и не забывай, что отчет нужно присылать каждый день до 21:00.";

        public static final String INFO_AFTER_ATTACHMENT = "Поздравляем! Вам доверили животное!\n" + "Заботьтесь о нем с любовью! А мы будем наблюдать за этим\uD83D\uDE09. " +
                "Поэтому будем ждать от вас ежедневный отчет в форме:\n" +
                "Отчет:\n1. Рацион животного.\n" +
                "2. Общее самочувствие и привыкание к новому месту.\n" +
                "3. Изменение в поведении: отказ от старых привычек, приобретение новых.\n" +
                "Обязательно прикрепи фото животного, и не забывай, что отчет нужно присылать до 21:00.";
        public static final String USER_CONTACT = "\u260E Контактные данные\nУкажите в формате:\n89225554433 Иван";
        public static final String COMPLETE_USER_CONTACT = "\uD83D\uDD8B Ваши контакты сохранены";
        public static final String CONFLICT_USER_CONTACT = "\u2753\u2753\u2753 Возникла ошибка\n(Оформление не соответствует)\n" +
                "\n1. Сначала выберите раздел:\n       \uD83D\uDD8B Принять контакты\n2. Запишите согласно образцу:\n(номер из 11 цифр)пробел(имя)";
        public static final String DEFAULT_MESSAGE = "Чтобы открыть начальное раскладное меню:\nжмите /start";
}
