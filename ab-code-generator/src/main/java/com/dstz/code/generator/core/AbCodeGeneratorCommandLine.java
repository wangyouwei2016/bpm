package com.dstz.code.generator.core;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Objects;
import java.util.Scanner;

public class AbCodeGeneratorCommandLine {

	private final AbCodeGenerator codeGenerator;

	public AbCodeGeneratorCommandLine(AbCodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}

	public void run() {
		AbCodeGeneratorModel codeGeneratorModel = new AbCodeGeneratorModel();
		Scanner scanner = new Scanner(System.in);

		System.out.println(StrUtil.center("填写信息", 30, '*'));

		System.out.print("保存目录（可选）：");
		codeGeneratorModel.setOutputDir(StrUtil.trim(scanner.nextLine()));

		System.out.print("作者（可选）：");
		codeGeneratorModel.setAuthor(StrUtil.trim(scanner.nextLine()));

		for (int i = 0; ; i++) {
			if (i > 0) {
				System.out.println(StrUtil.center("填写信息", 30, '*'));
				codeGeneratorModel.setPackageName(null);
				codeGeneratorModel.setIncludeTable(null);
				codeGeneratorModel.setExcludeTable(null);
			}

			// 填写包名
			while (StrUtil.isBlank(codeGeneratorModel.getPackageName())) {
				System.out.print("包名（必填）：");
				codeGeneratorModel.setPackageName(StrUtil.trim(scanner.nextLine()));
			}

			System.out.print("指定表（可选，多表逗号(,)分隔）：");
			String line = StrUtil.trimToNull(scanner.nextLine());
			codeGeneratorModel.setIncludeTable(Objects.isNull(line) ? null : line.split(StrUtil.COMMA));

			if (ArrayUtil.isEmpty(codeGeneratorModel.getIncludeTable())) {
				System.out.print("排除表（可选，多表逗号(,)分隔）：");
				line = StrUtil.trimToNull(scanner.nextLine());
				codeGeneratorModel.setExcludeTable(Objects.isNull(line) ? null : line.split(StrUtil.COMMA));
			}

			System.out.println(StrUtil.center("生成信息", 30, '*'));
			System.out.printf("保存目录：%s\n", codeGeneratorModel.getOutputDir());
			System.out.printf("作者：%s\n", codeGeneratorModel.getAuthor());
			System.out.printf("包名：%s\n", codeGeneratorModel.getPackageName());
			System.out.printf("指定表：%s\n", StrUtil.nullToEmpty(ArrayUtil.join(codeGeneratorModel.getIncludeTable(), StrUtil.COMMA)));
			System.out.printf("排除表：%s\n", StrUtil.nullToEmpty(ArrayUtil.join(codeGeneratorModel.getExcludeTable(), StrUtil.COMMA)));

			System.out.println(StrUtil.repeat('*', 30));

			System.out.print("（Y：确认 N：重新填写  E：退出）：");

			String cmd = StrUtil.trimToEmpty(scanner.nextLine());
			if (StrUtil.equalsIgnoreCase(cmd, "Y")) {
				invokeGenerator(codeGeneratorModel);
				System.out.println(StrUtil.repeat('*', 30));
				System.out.print("是否继续（Y/N）：");
				if (!StrUtil.equalsIgnoreCase(StrUtil.trimToEmpty(scanner.nextLine()), "Y")) {
					System.exit(0);
				}
			} else if (StrUtil.equalsIgnoreCase(cmd, "E")) {
				System.exit(0);
			}
		}
	}

	private void invokeGenerator(AbCodeGeneratorModel codeGeneratorModel) {
		try {
			codeGenerator.run(codeGeneratorModel);
		} catch (AlertMessageException e) {
			System.err.println(StrUtil.center("错误信息", 30, '*'));
			System.out.println(e.getMessage());
		}
	}


}
