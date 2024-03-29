USE [Core]
GO

/****** Object:  Table [contact].[SALUTATION]    Script Date: 26-Sep-19 3:41:54 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [contact].[SALUTATION](
	[TITLE] [int] NOT NULL,
	[SALUTATION] [varchar](30) NOT NULL,
 CONSTRAINT [SALUTATION_PK] PRIMARY KEY CLUSTERED 
(
	[SALUTATION] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [USERSPACE]
) ON [USERSPACE]

GO

ALTER TABLE [contact].[SALUTATION] SET (LOCK_ESCALATION = AUTO)
GO

