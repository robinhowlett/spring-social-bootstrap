package org.springframework.social.shell.converters.tables;

import com.bethecoder.ascii_table.ASCIITable;
import com.bethecoder.ascii_table.ASCIITableHeader;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.shell.converters.CliPrinterTypeConverter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 5/19/15.
 */
public class TableCliPrinterTypeConverter implements CliPrinterTypeConverter<Object> {

    private List<ASCIITableHeader> tableHeaders = null;
    private String expression = null;

    /**
     * This isn't bombproof...
     *
     * @param parameters
     */
    @Override
    public void setParameters(String parameters) {
        Assert.notNull(parameters);

        List<int[][]> labels = new ArrayList<>();

        parameters = parameters.replaceAll("\\s", ""); // remove whitespace

        String[] headersValues = parameters.split("\\|");
        String headers = headersValues[0];
        String value = headersValues[1];

        while (headers.charAt(0) == '{') {
            headers = headers.substring(1);
        } // be gone {
        while (headers.charAt(headers.length() - 1) == '}') {
            headers = headers.substring(0, headers.length() - 1);
        } // be gone }

        tableHeaders = new ArrayList<>();

        ArrayList<String> headerLabels = Lists.newArrayList(Splitter.on(",").split(headers)); // split on commas
        for (int i = 0; i < headerLabels.size(); i++) {
            String headerName = headerLabels.get(i).trim();

            int dataAlign = 1;
            if (headerName.charAt(headerName.length() - 1) == '<') {  // < = left align
                dataAlign = -1;
                headerName = headerName.substring(0, headerName.length() - 1);
            } else if (headerName.charAt(headerName.length() - 1) == '>') {   // > = right align
                dataAlign = 1;
                headerName = headerName.substring(0, headerName.length() - 1);
            } else if (headerName.charAt(headerName.length() - 1) == '_') {   // > = center align
                dataAlign = 0;
                headerName = headerName.substring(0, headerName.length() - 1);
            }

            tableHeaders.add(new ASCIITableHeader(headerName, dataAlign));
        }

        expression = value.trim();
    }

    @Override
    public String convert(Object o) {
        Assert.notNull(tableHeaders);
        Assert.notNull(expression);

        ASCIITableHeader[] tableHeaders = this.tableHeaders.toArray(new ASCIITableHeader[this.tableHeaders.size()]);
        String[][] cells = null;

        SpelExpressionParser parser = new SpelExpressionParser();
        Expression spelExpression = parser.parseExpression(expression);
        List<List<String>> values = (List<List<String>>) spelExpression.getValue(o);

        List<String[]> rows = new ArrayList<>();
        for (Integer i = 0; i < values.size(); i++) {
            List<String> tableRow = new ArrayList<>();
            tableRow.add(Integer.toString(i + 1));

            List<String> row = values.get(i);
            for (String column : row) {
                tableRow.add(column);
            }

            rows.add(tableRow.toArray(new String[tableRow.size()]));
        }

        if (!rows.isEmpty()) {
            cells = rows.toArray(new String[rows.size()][]);
        }

        return ASCIITable.getInstance().getTable(tableHeaders, cells);
    }

    public List<ASCIITableHeader> getTableHeaders() {
        return tableHeaders;
    }

    public void setTableHeaders(List<ASCIITableHeader> tableHeaders) {
        this.tableHeaders = tableHeaders;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
