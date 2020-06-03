package com.jodi.cophat.helper

import android.content.Context
import android.util.AndroidException
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.*
import org.apache.poi.hssf.record.cf.BorderFormatting
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.util.CellRangeAddress
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

interface ExportListener {
    fun onExportSuccess()
    fun onExportFailed()
}

class ExportWorkbook(private val context: Context, private val resourceManager: ResourceManager) {

    private val headerRow = 0
    private val descriptionColumn = 0
    private var questionsSize = 38
    private val subQuestionsSize = 51
    private val workBook = HSSFWorkbook()
    private lateinit var boldStyle: HSSFCellStyle
    private lateinit var centerWhiteStyle: HSSFCellStyle
    private lateinit var centerCornflowerStyle: HSSFCellStyle
    private lateinit var centerGreenStyle: HSSFCellStyle
    private lateinit var centerYellowStyle: HSSFCellStyle
    private lateinit var centerBlueStyle: HSSFCellStyle
    private lateinit var questions: List<Question>

    fun exportQuestionnaires(
        questionnaires: Array<QuestionnaireReport>,
        categories: List<Category>,
        questionsList: List<Question>?,
        listener: ExportListener
    ) {
        questionsList?.let {
            questions = it
            questionsSize = it.size
        }
        generateCenterColoredStyles()
        generateBoldStyle()

        val sheetChildren =
            workBook.createSheet(resourceManager.getString(R.string.children_adolescents))
        createHeader(sheetChildren, questionnaires, "children")

        val sheetSubChildren =
            workBook.createSheet(resourceManager.getString(R.string.sub_children))
        createSubHeader(sheetSubChildren, questionnaires)
//
        val sheetParents =
            workBook.createSheet(resourceManager.getString(R.string.parents_responsible))
        createHeader(sheetParents, questionnaires, "parent")

        val sheetSubParents =
            workBook.createSheet(resourceManager.getString(R.string.sub_parents))
        createSubHeader(sheetSubParents, questionnaires)

//        val sheetComparative = workBook.createSheet(resourceManager.getString(R.string.comparative))
//
//        val sheetSubComparative =
//            workBook.createSheet(resourceManager.getString(R.string.sub_comparative))

        var answerColumn = descriptionColumn + 1
        var answerColumnComparative = descriptionColumn + 1
        // Children Questions
        generateQuestionnaire(sheetChildren, questionnaires, categories, "children")
        generateSubQuestionnaire(sheetSubChildren, questionnaires, categories, "children")
        // Parents Questions
        generateQuestionnaire(sheetParents, questionnaires, categories, "parent")
        generateSubQuestionnaire(sheetSubParents, questionnaires, categories, "parent")

//        for (questionnaire in questionnaires) {
//            questionnaire.childApplication?.let { application -> // Aqui
//                generateQuestionnaire(sheetChildren, application, categories, answerColumn)
//                generateSubQuestionnaire(sheetSubChildren, application, categories, answerColumn)
//            }
//
//            questionnaire.parentApplication?.let { application ->
//                generateQuestionnaire(sheetParents, application, categories, answerColumn)
//                generateSubQuestionnaire(sheetSubParents, application, categories, answerColumn)
//            }
//
//            questionnaire.childApplication?.let { childApplication ->
//                questionnaire.parentApplication.let { parentApplication ->
//                    createCategoriesComparative(sheetComparative, categories)
//                    createCategoriesComparative(sheetSubComparative, categories)
//                    val childrenAnswers = childApplication.answers?.values
//                        ?.sortedBy { answer -> answer.id }
//                    val parentsAnswers = parentApplication?.answers?.values
//                        ?.sortedBy { answer -> answer.id }
//                    createMainHeaderComparative(
//                        sheetComparative,
//                        questionnaire.identifyCode
//                    )
//                    createSubTotalsComparative(
//                        sheetComparative,
//                        categories,
//                        childrenAnswers,
//                        parentsAnswers,
//                        answerColumnComparative,
//                        answerColumn
//                    )
//                    createTotalsComparative(
//                        sheetComparative,
//                        childrenAnswers,
//                        parentsAnswers,
//                        answerColumnComparative
//                    )
//
//                    createMainHeaderComparativeSubQuestions(
//                        sheetSubComparative,
//                        questionnaire.identifyCode
//                    )
//                    createSubTotalsComparativeSubQuestions(
//                        sheetSubComparative,
//                        categories,
//                        childrenAnswers,
//                        parentsAnswers,
//                        answerColumnComparative,
//                        answerColumn
//                    )
//                    createTotalsComparativeSubQuestions(
//                        sheetSubComparative,
//                        childrenAnswers,
//                        parentsAnswers,
//                        answerColumnComparative
//                    )
//                    answerColumnComparative += 2
//                }
//            }
//            answerColumn++
//        }

        if (questionnaires.size > 1) {
            generateFile(resourceManager.getString(R.string.cophat), listener)
        } else {
            generateFile(questionnaires[0].identifyCode, listener)
        }
    }


    private fun generateQuestionnaire(
        sheet: HSSFSheet,
        questionnaires: Array<QuestionnaireReport>,
        categories: List<Category>,
        type: String
    ) {
        createQuestions(sheet)
        createDescriptions(sheet)
        createCategories(sheet, categories)
        var allAnswers: MutableList<Answer> = ArrayList()

        questionnaires.map { questionnaire ->
            if (type.equals("children")) {
                val answers = questionnaire.childApplication?.answers!!.let {
                    it.values.sortedBy { answer -> answer.id }
                }
                answers.map { ans ->
                    allAnswers.add(ans)
                }
            } else {
                val answers = questionnaire.parentApplication?.answers!!.let {
                    it.values.sortedBy { answer -> answer.id }
                }
                answers.map { ans ->
                    allAnswers.add(ans)
                }
            }
        }
        createAnswers(sheet, allAnswers)
        createTotals(sheet, allAnswers)
        createSubTotals(sheet, categories, allAnswers)
    }

    private fun generateSubQuestionnaire(
        sheet: HSSFSheet,
        questionnaires: Array<QuestionnaireReport>,
        categories: List<Category>,
        type: String
    ) {
        createSubQuestions(sheet)
        createSubDescriptions(sheet)
        createSubCategories(sheet, categories)
        var allAnswers: MutableList<Answer> = ArrayList()

        questionnaires.map { questionnaire ->
            if (type.equals("children")) {
                val answers = questionnaire.childApplication?.answers!!.let {
                    it.values.sortedBy { answer -> answer.id }
                }
                answers.map { ans ->
                    allAnswers.add(ans)
                }
            } else {
                val answers = questionnaire.parentApplication?.answers!!.let {
                    it.values.sortedBy { answer -> answer.id }
                }
                answers.map { ans ->
                    allAnswers.add(ans)
                }
            }
        }

        createSubAnswers(sheet, allAnswers)
        createTotalsSubQuestions(sheet, allAnswers)
        createSubTotalsSubQuestions(sheet, categories, allAnswers)
    }

    private fun generateCenterColoredStyles() {
        centerWhiteStyle = workBook.createCellStyle().apply {
            addAlignment(this)
            addBorders(this)
            fillPattern = HSSFCellStyle.SOLID_FOREGROUND
            fillForegroundColor = HSSFColor.WHITE.index
        }

        centerCornflowerStyle = workBook.createCellStyle().apply {
            addAlignment(this)
            addBorders(this)
            fillPattern = HSSFCellStyle.SOLID_FOREGROUND
            fillForegroundColor = HSSFColor.LIGHT_CORNFLOWER_BLUE.index
        }

        centerGreenStyle = workBook.createCellStyle().apply {
            addAlignment(this)
            addBorders(this)
            fillPattern = HSSFCellStyle.SOLID_FOREGROUND
            fillForegroundColor = HSSFColor.LIGHT_GREEN.index
        }

        centerYellowStyle = workBook.createCellStyle().apply {
            addAlignment(this)
            addBorders(this)
            fillPattern = HSSFCellStyle.SOLID_FOREGROUND
            fillForegroundColor = HSSFColor.LIGHT_YELLOW.index
        }

        centerBlueStyle = workBook.createCellStyle().apply {
            addAlignment(this)
            addBorders(this)
            fillPattern = HSSFCellStyle.SOLID_FOREGROUND
            fillForegroundColor = HSSFColor.PALE_BLUE.index
        }
    }

    private fun generateBoldStyle() {
        boldStyle = workBook.createCellStyle().apply {
            addFontBold(this)
            addAlignment(this)
            addBorders(this)
        }
    }

    private fun addFontBold(style: HSSFCellStyle) {
        val boldFont = workBook.createFont()
        boldFont.boldweight = HSSFFont.BOLDWEIGHT_BOLD

        style.apply {
            setFont(boldFont)
            alignment = CellStyle.ALIGN_CENTER
            verticalAlignment = CellStyle.VERTICAL_CENTER
        }
    }

    private fun addAlignment(style: HSSFCellStyle) {
        style.apply {
            alignment = CellStyle.ALIGN_CENTER
            verticalAlignment = CellStyle.VERTICAL_CENTER
        }
    }

    private fun addBorders(style: HSSFCellStyle) {
        style.apply {
            borderBottom = BorderFormatting.BORDER_THIN
            borderTop = BorderFormatting.BORDER_THIN
            borderLeft = BorderFormatting.BORDER_THIN
            borderRight = BorderFormatting.BORDER_THIN
        }
    }

    private fun getStyleByCategory(categoryType: CategoryType?): HSSFCellStyle {
        return when (categoryType) {
            CategoryType.UNDERSTANDING_DISEASE -> centerWhiteStyle
            CategoryType.HOSPITALIZATION -> centerCornflowerStyle
            CategoryType.TREATMENT_SUCCESS -> centerGreenStyle
            CategoryType.COLLATERAL_EFFECTS -> centerYellowStyle
            CategoryType.SCHOOL_EXPECTATION -> centerBlueStyle
            else -> centerWhiteStyle
        }
    }

    private fun createHeader(
        sheet: HSSFSheet,
        questionnaires: Array<QuestionnaireReport>,
        type: String
    ) {
        val headerRow = sheet.createRow(headerRow)
        headerRow.createCell(descriptionColumn).apply {
            setCellValue(resourceManager.getString(R.string.questions))
            setCellStyle(boldStyle)
        }

        var answerColumn = descriptionColumn + 1
        for (questionnaire in questionnaires) {
            if (type.equals("children")) {
                headerRow.createCell(answerColumn).apply {
                    setCellValue("${questionnaire.identifyCode} - ${questionnaire.patient?.name}")
                    setCellStyle(boldStyle)
                }
            } else {
                headerRow.createCell(answerColumn).apply {
                    setCellValue("${questionnaire.identifyCode} - ${questionnaire.parentApplication?.intervieweeName}")
                    setCellStyle(boldStyle)
                }
            }

            sheet.setColumnWidth(answerColumn, 6000)
            answerColumn++
        }
    }

    private fun createSubHeader(sheet: HSSFSheet, questionnaires: Array<QuestionnaireReport>) {
        val headerRow = sheet.createRow(headerRow)
        headerRow.createCell(descriptionColumn).apply {
            setCellValue(resourceManager.getString(R.string.sub_questions))
            setCellStyle(boldStyle)
        }

        var subAnswerColumn = descriptionColumn + 1
        for (questionnaire in questionnaires) {
            headerRow.createCell(subAnswerColumn).apply {
                setCellValue(questionnaire.identifyCode)
                setCellStyle(boldStyle)
            }
            sheet.setColumnWidth(subAnswerColumn, 6000)
            subAnswerColumn++
        }
    }

    private fun createQuestions(sheet: HSSFSheet) {
        var questionsRow: HSSFRow
        for (position in 1..questionsSize) {
            questionsRow = sheet.createRow(position)
            questionsRow.createCell(descriptionColumn).apply {
                setCellValue("$position")
                setCellStyle(getStyleByCategory(questions[position - 1].category))
            }
        }
    }

    private fun createSubQuestions(sheet: HSSFSheet) {
        var subAnswerRow = 1
        var subQuestionsRow: HSSFRow
        questions.map { question ->
            question.subQuestions
                ?.values
                ?.sortedBy { subQuestion -> subQuestion.id }
                ?.map { subQuestion ->
                    subQuestion.alternatives
                        ?.values
                        ?.sortedBy { alternative -> alternative.id }
                        ?.map { alternative ->
                            subQuestionsRow = sheet.createRow(subAnswerRow)
                            subQuestionsRow.createCell(descriptionColumn).apply {
                                setCellValue("Q${question.id}-${subQuestion.id}.${alternative.id}")
                                setCellStyle(getStyleByCategory(questions[question.id!! - 1].category))
                            }
                            subAnswerRow++
                        }
                }
        }
    }

    private fun createDescriptions(sheet: HSSFSheet) { // Abas questões
        val categoriesPositionRow = questionsSize + 3
        val categoriesHeaderRow = sheet.createRow(categoriesPositionRow)
        categoriesHeaderRow.createCell(descriptionColumn).apply {
            setCellValue(resourceManager.getString(R.string.category))
            setCellStyle(boldStyle)
        }

        val totalPositionRow = questionsSize + 1
        val totalDescriptionRow = sheet.createRow(totalPositionRow)
        totalDescriptionRow.createCell(descriptionColumn).apply {
            setCellValue(resourceManager.getString(R.string.total))
            setCellStyle(boldStyle)
        }
    }

    private fun createSubDescriptions(sheet: HSSFSheet) { // Abas subquestões
        val categoriesPositionRow = subQuestionsSize + 3
        val categoriesHeaderRow = sheet.createRow(categoriesPositionRow)
        categoriesHeaderRow.createCell(descriptionColumn).apply {
            setCellValue(resourceManager.getString(R.string.category))
            setCellStyle(boldStyle)
        }

        val totalPositionRow = subQuestionsSize + 1
        val totalDescriptionRow = sheet.createRow(totalPositionRow)
        totalDescriptionRow.createCell(descriptionColumn).apply {
            setCellValue(resourceManager.getString(R.string.total))
            setCellStyle(boldStyle)
        }
    }

    private fun createCategories(sheet: HSSFSheet, categories: List<Category>) {
        var categoriesPositionRow = questionsSize + 4

        var categoriesRow: HSSFRow
        for (category in categories) {
            categoriesRow = sheet.createRow(categoriesPositionRow)
            categoriesRow.createCell(descriptionColumn).apply {
                setCellValue(category.description)
                setCellStyle(getStyleByCategory(category.type))
            }
            categoriesPositionRow++
        }
        sheet.setColumnWidth(descriptionColumn, 7500)
    }

    private fun createSubCategories(sheet: HSSFSheet, categories: List<Category>) {
        var categoriesPositionRow = subQuestionsSize + 4

        var categoriesRow: HSSFRow
        for (category in categories) {
            categoriesRow = sheet.createRow(categoriesPositionRow)
            categoriesRow.createCell(descriptionColumn).apply {
                setCellValue(category.description)
                setCellStyle(getStyleByCategory(category.type))
            }
            categoriesPositionRow++
        }
        sheet.setColumnWidth(descriptionColumn, 7500)
    }

    private fun createCategoriesComparative(sheet: HSSFSheet, categories: List<Category>) {
        val categoriesHeaderRow = sheet.createRow(1)
        categoriesHeaderRow.createCell(descriptionColumn).apply {
            setCellValue(resourceManager.getString(R.string.category))
            setCellStyle(boldStyle)
        }

        var categoriesPositionRow = 2

        var categoriesRow: HSSFRow
        for (category in categories) {
            categoriesRow = sheet.createRow(categoriesPositionRow)
            categoriesRow.createCell(descriptionColumn).apply {
                setCellValue(category.description)
                setCellStyle(getStyleByCategory(category.type))
            }
            categoriesPositionRow++
        }
        sheet.setColumnWidth(descriptionColumn, 7500)
    }

    private fun createAnswers(sheet: HSSFSheet, answers: MutableList<Answer>) {
        var answerRow: HSSFRow
        var answer: Int?
        for (position in 1..answers?.size!!) {
            var pos = position
            var col = 0
            while (pos > questions.size) {
                pos = pos.minus(questions.size)
            }
            if (position % questions.size != 0) {
                col = position.div(questions.size).plus(1)
            } else {
                col = position.div(questions.size)
            }
            answerRow = sheet.getRow(pos)
            answer = answers?.get(position - 1)?.chosenAnswer?.chosenAnswerPoints
            answerRow.createCell(col).apply {
                setCellValue("$answer")
                setCellStyle(getStyleByCategory(questions[pos - 1].category))
            }
        }
    }

    private fun createSubAnswers(sheet: HSSFSheet, answers: List<Answer>) {
        var subAnswerRow: HSSFRow
        var subAnswerRowPosition = 1
        var subAnswerPoints: Int?

        var tempAswers: MutableList<Answer> = ArrayList()
        for (index in 1..answers?.size!!) {
            var col = 0
            if (index % questions.size != 0) {
                col = index.div(questions.size).plus(1)
                tempAswers.add(answers[index - 1])
            } else {
                col = index.div(questions.size)
                tempAswers.add(answers[index - 1])

                questions.map { question ->
                    question.subQuestions
                        ?.values
                        ?.sortedBy { subQuestion -> subQuestion.id }
                        ?.map { subQuestion ->
                            subQuestion.alternatives
                                ?.values
                                ?.sortedBy { alternative -> alternative.id }
                                ?.map { alternative ->
                                    subAnswerRow = sheet.getRow(subAnswerRowPosition)
                                    subAnswerPoints = try {
                                        tempAswers[question.id!! - 1].subAnswers
                                            ?.values
                                            ?.sortedBy { subQuestion -> subQuestion.id }
                                            ?.get(subQuestion.id!! - 1)
                                            ?.alternatives
                                            ?.values
                                            ?.sortedBy { alternativeChosen -> alternativeChosen.id }
                                            ?.get(alternative.id!! - 1)
                                            ?.chosenSubAnswer
                                            ?.chosenAnswerPoints ?: 0
                                    } catch (e: Exception) {
                                        0
                                    }
                                    subAnswerRow.createCell(col).apply {
                                        setCellValue("$subAnswerPoints")
                                        setCellStyle(getStyleByCategory(questions[question.id!! - 1].category))
                                    }
                                    subAnswerRowPosition++
                                }
                        }
                }
                tempAswers.clear()
                subAnswerRowPosition = 1
            }
        }


    }

    private fun createTotals(sheet: HSSFSheet, answers: List<Answer>) {
        val totalPositionRow = questionsSize + 1
        val totalRow: HSSFRow = sheet.getRow(totalPositionRow)

        var total = 0
        for (index in 1..answers?.size!!) {
            total += answers[index.minus(1)].chosenAnswer?.chosenAnswerPoints ?: 0
            var col = 0
            if (index % questions.size != 0) {
                col = index.div(questions.size).plus(1)
            } else {
                col = index.div(questions.size)
                totalRow.createCell(col).apply {
                    setCellValue("$total")
                    setCellStyle(boldStyle)
                }
                total = 0
            }
        }
    }

    private fun createTotalsSubQuestions(
        sheet: HSSFSheet,
        answers: List<Answer>
    ) {
        val totalPositionRow = subQuestionsSize + 1
        val totalRow: HSSFRow = sheet.getRow(totalPositionRow)

        var total = 0
        for (index in 1..answers?.size!!) {
            total += (answers[index - 1].subAnswers?.values?.sumBy { subAnswer ->
                subAnswer.alternatives?.values?.sumBy { alternative ->
                    alternative.chosenSubAnswer?.chosenAnswerPoints ?: 0
                } ?: 0
            } ?: 0)
            var col = 0
            if (index % questions.size != 0) {
                col = index.div(questions.size).plus(1)
            } else {
                col = index.div(questions.size)
                totalRow.createCell(col).apply {
                    setCellValue("$total")
                    setCellStyle(boldStyle)
                }
                total = 0
            }
        }
    }

    private fun createTotalsComparative(
        sheet: HSSFSheet,
        childrenAnswers: List<Answer>?,
        parentsAnswers: List<Answer>?,
        answerColumnComparative: Int
    ) {
        val totalPositionRow = 7
        val totalRow: HSSFRow = sheet.createRow(totalPositionRow)

        val totalDescriptionRow = sheet.createRow(totalPositionRow)
        totalDescriptionRow.createCell(descriptionColumn).apply {
            setCellValue(resourceManager.getString(R.string.total))
            setCellStyle(boldStyle)
        }

        var childrenTotal = 0
        childrenAnswers?.let {
            for (answer in childrenAnswers) {
                childrenTotal += (answer.chosenAnswer?.chosenAnswerPoints ?: 0)
            }
        }
        totalRow.createCell(answerColumnComparative).apply {
            setCellValue("$childrenTotal")
            setCellStyle(boldStyle)
        }

        var parentsTotal = 0
        parentsAnswers?.let {
            for (answer in parentsAnswers) {
                parentsTotal += (answer.chosenAnswer?.chosenAnswerPoints ?: 0)
            }
        }
        totalRow.createCell(answerColumnComparative + 1).apply {
            setCellValue("$parentsTotal")
            setCellStyle(boldStyle)
        }
    }

    private fun createTotalsComparativeSubQuestions(
        sheet: HSSFSheet,
        childrenAnswers: List<Answer>?,
        parentsAnswers: List<Answer>?,
        answerColumnComparative: Int
    ) {
        val totalPositionRow = 7
        val totalRow: HSSFRow = sheet.createRow(totalPositionRow)

        val totalDescriptionRow = sheet.createRow(totalPositionRow)
        totalDescriptionRow.createCell(descriptionColumn).apply {
            setCellValue(resourceManager.getString(R.string.total))
            setCellStyle(boldStyle)
        }

        var childrenTotal = 0
        childrenAnswers?.let {
            for (answer in childrenAnswers) {
                childrenTotal += (answer.subAnswers?.values?.sumBy { subAnswer ->
                    subAnswer.alternatives?.values?.sumBy { alternative ->
                        alternative.chosenSubAnswer?.chosenAnswerPoints ?: 0
                    } ?: 0
                } ?: 0)
            }
        }
        totalRow.createCell(answerColumnComparative).apply {
            setCellValue("$childrenTotal")
            setCellStyle(boldStyle)
        }

        var parentsTotal = 0
        parentsAnswers?.let {
            for (answer in parentsAnswers) {
                parentsTotal += (answer.subAnswers?.values?.sumBy { subAnswer ->
                    subAnswer.alternatives?.values?.sumBy { alternative ->
                        alternative.chosenSubAnswer?.chosenAnswerPoints ?: 0
                    } ?: 0
                } ?: 0)
            }
        }
        totalRow.createCell(answerColumnComparative + 1).apply {
            setCellValue("$parentsTotal")
            setCellStyle(boldStyle)
        }
    }

    private fun createSubTotals(
        sheet: HSSFSheet,
        categories: List<Category>,
        answers: List<Answer>
    ) {
        var tempAswers: MutableList<Answer> = ArrayList()
        for (index in 1..answers?.size!!) {
            var categoriesPositionRow = questionsSize + 4
            var categoriesRow: HSSFRow
            var col = 0
            if (index % questions.size != 0) {
                col = index.div(questions.size).plus(1)
                tempAswers.add(answers[index - 1])
            } else {
                col = index.div(questions.size)
                tempAswers.add(answers[index - 1])
                for (category in categories) {
                    categoriesRow = sheet.getRow(categoriesPositionRow)

                    val categoryPoints =
                        tempAswers.filter { questions[it.id - 1].category == category.type }
                            .sumBy { it.chosenAnswer?.chosenAnswerPoints ?: 0 }
                    categoriesRow.createCell(col).apply {
                        setCellValue("$categoryPoints")
                        setCellStyle(getStyleByCategory(category.type))
                    }
                    categoriesPositionRow++
                }
                tempAswers.clear()
            }
        }
    }

    private fun createSubTotalsSubQuestions(
        sheet: HSSFSheet,
        categories: List<Category>,
        answers: List<Answer>
    ) {
        var tempAswers: MutableList<Answer> = ArrayList()
        for (index in 1..answers?.size!!) {
            var categoriesPositionRow = subQuestionsSize + 4
            var categoriesRow: HSSFRow
            var col = 0
            if (index % questions.size != 0) {
                col = index.div(questions.size).plus(1)
                tempAswers.add(answers[index - 1])
            } else {
                col = index.div(questions.size)
                tempAswers.add(answers[index - 1])
                for (category in categories) {
                    categoriesRow = sheet.getRow(categoriesPositionRow)

                    val categoryPoints =
                        tempAswers.filter { questions[it.id - 1].category == category.type }
                            .sumBy { answer ->
                                answer.subAnswers?.values?.sumBy { subAnswer ->
                                    subAnswer.alternatives?.values?.sumBy { alternative ->
                                        alternative.chosenSubAnswer?.chosenAnswerPoints ?: 0
                                    } ?: 0
                                } ?: 0
                            }
                    categoriesRow.createCell(col).apply {
                        setCellValue("$categoryPoints")
                        setCellStyle(getStyleByCategory(category.type))
                    }
                    categoriesPositionRow++
                }
                tempAswers.clear()
            }
        }
    }

    private fun createMainHeaderComparative( // header aba Questões CA x P
        sheet: HSSFSheet,
        identifyCode: String?
    ) {
        val subTotalHeader = sheet.createRow(0).createCell(0)
        sheet.addMergedRegion(CellRangeAddress(0, 0, 0, 2))
        subTotalHeader.setCellValue(identifyCode)
        subTotalHeader.setCellStyle(boldStyle)

    }

    private fun createSubTotalsComparative( // colunas aba Questões CA x P
        sheet: HSSFSheet,
        categories: List<Category>,
        childrenAnswers: List<Answer>?,
        parentsAnswers: List<Answer>?,
        answerColumnComparative: Int,
        answerColumn: Int
    ) {
        val subTotalHeaderChild = sheet.getRow(1).createCell(answerColumn)
        subTotalHeaderChild.setCellValue(resourceManager.getString(R.string.child))
        subTotalHeaderChild.setCellStyle(boldStyle)

        val subTotalHeaderParents = sheet.getRow(1).createCell(answerColumn + 1)
        subTotalHeaderParents.setCellValue(resourceManager.getString(R.string.parent))
        subTotalHeaderParents.setCellStyle(boldStyle)

        var categoriesPositionRow = 2

        var categoriesRow: HSSFRow
        for (category in categories) {
            categoriesRow = sheet.getRow(categoriesPositionRow)

            val categoryChildrenPoints =
                childrenAnswers?.filter { questions[it.id - 1].category == category.type }
                    ?.sumBy { it.chosenAnswer?.chosenAnswerPoints ?: 0 }
            categoriesRow.createCell(answerColumnComparative).apply {
                setCellValue("$categoryChildrenPoints")
                setCellStyle(getStyleByCategory(category.type))
            }

            val categoryParentsPoints =
                parentsAnswers?.filter { questions[it.id - 1].category == category.type }
                    ?.sumBy { it.chosenAnswer?.chosenAnswerPoints ?: 0 }
            categoriesRow.createCell(answerColumnComparative + 1).apply {
                setCellValue("$categoryParentsPoints")
                setCellStyle(getStyleByCategory(category.type))
            }

            categoriesPositionRow++
        }
        sheet.setColumnWidth(answerColumnComparative, 3500)
        sheet.setColumnWidth(answerColumnComparative + 1, 3500)
    }

    private fun createMainHeaderComparativeSubQuestions( // header aba Subquestões CA x P
        sheet: HSSFSheet,
        identifyCode: String?
    ) {
        val subTotalHeaderSubQuestions = sheet.createRow(0).createCell(0)
        sheet.addMergedRegion(CellRangeAddress(0, 0, 0, 2))
        subTotalHeaderSubQuestions.setCellValue(identifyCode)
        subTotalHeaderSubQuestions.setCellStyle(boldStyle)

    }

    private fun createSubTotalsComparativeSubQuestions( // colunas aba Subquestões CA x P
        sheet: HSSFSheet,
        categories: List<Category>,
        childrenAnswers: List<Answer>?,
        parentsAnswers: List<Answer>?,
        answerColumnComparative: Int,
        answerColumn: Int
    ) {
        val subTotalHeaderChild = sheet.getRow(1).createCell(answerColumn)
        subTotalHeaderChild.setCellValue(resourceManager.getString(R.string.child))
        subTotalHeaderChild.setCellStyle(boldStyle)

        val subTotalHeaderParents = sheet.getRow(1).createCell(answerColumn + 1)
        subTotalHeaderParents.setCellValue(resourceManager.getString(R.string.parent))
        subTotalHeaderParents.setCellStyle(boldStyle)

        var categoriesPositionRow = 2

        var categoriesRow: HSSFRow
        for (category in categories) {
            categoriesRow = sheet.getRow(categoriesPositionRow)

            val categoryChildrenPoints =
                childrenAnswers?.filter { questions[it.id - 1].category == category.type }
                    ?.sumBy { answer ->
                        answer.subAnswers?.values?.sumBy { subAnswer ->
                            subAnswer.alternatives?.values?.sumBy { alternative ->
                                alternative.chosenSubAnswer?.chosenAnswerPoints ?: 0
                            } ?: 0
                        } ?: 0
                    }
            categoriesRow.createCell(answerColumnComparative).apply {
                setCellValue("$categoryChildrenPoints")
                setCellStyle(getStyleByCategory(category.type))
            }

            val categoryParentsPoints =
                parentsAnswers?.filter { questions[it.id - 1].category == category.type }
                    ?.sumBy { answer ->
                        answer.subAnswers?.values?.sumBy { subAnswer ->
                            subAnswer.alternatives?.values?.sumBy { alternative ->
                                alternative.chosenSubAnswer?.chosenAnswerPoints ?: 0
                            } ?: 0
                        } ?: 0
                    }
            categoriesRow.createCell(answerColumnComparative + 1).apply {
                setCellValue("$categoryParentsPoints")
                setCellStyle(getStyleByCategory(category.type))
            }

            categoriesPositionRow++
        }
        sheet.setColumnWidth(answerColumnComparative, 3500)
        sheet.setColumnWidth(answerColumnComparative + 1, 3500)
    }

    private fun generateFile(identifyCode: String?, listener: ExportListener) {
        val file = File(context.getExternalFilesDir(null), "$identifyCode.xls")
        try {
            val os = FileOutputStream(file, true)
            workBook.write(os)
            os.close()
            listener.onExportSuccess()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            listener.onExportFailed()
        } catch (e: IOException) {
            e.printStackTrace()
            listener.onExportFailed()
        }
    }
}